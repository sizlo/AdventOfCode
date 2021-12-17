package advent.of.code.day16

import advent.of.code.day16.PacketDecoder.LengthType.*
import advent.of.code.utils.productOf
import advent.of.code.utils.readInput

class PacketDecoder {
    class HexadecimalTransmission(private var bits: String) {
        companion object {
            fun fromHexString(hexString: String): HexadecimalTransmission {
                val bits = hexString
                    .toList()
                    .joinToString("") {
                        it
                            .digitToInt(16)
                            .toString(2)
                            .padStart(4, '0')
                    }
                return HexadecimalTransmission(bits)
            }
        }

        fun hasData(): Boolean = bits.contains('1')

        fun consumeBits(numBits: Int): String {
            val consumedBits = bits.substring(0, numBits)
            bits = bits.removeRange(0, numBits)
            return consumedBits
        }

        fun consumeInt(numBits: Int): Int = consumeBits(numBits).toInt(2)

        fun consumeBoolean(): Boolean = consumeInt(1) == 1
    }

    enum class PacketType(private val id: Int) {
        SUM(0),
        PRODUCT(1),
        MINIMUM(2),
        MAXIMUM(3),
        LITERAL(4),
        GREATER_THAN(5),
        LESS_THAN(6),
        EQUAL_TO(7);

        companion object {
            fun fromTypeId(id: Int): PacketType = values().first { it.id == id }
        }
    }

    enum class LengthType {
        LENGTH_IN_BITS, NUMBER_OF_SUB_PACKETS;

        companion object {
            fun fromInt(int: Int): LengthType = if (int == 0) LENGTH_IN_BITS else NUMBER_OF_SUB_PACKETS
        }
    }

    class PacketHeader(transmission: HexadecimalTransmission) {
        val version = transmission.consumeInt(3)
        val type = PacketType.fromTypeId(transmission.consumeInt(3))
    }

    interface PacketPayload {
        fun getValue(): Long
    }

    class LiteralPacketPayload(transmission: HexadecimalTransmission): PacketPayload {
        private val value: Long
        init {
            var moreGroups = true
            var valueBits = ""
            while (moreGroups) {
                moreGroups = transmission.consumeBoolean()
                valueBits += transmission.consumeBits(4)
            }
            value = valueBits.toLong(2)
        }

        override fun getValue(): Long = value
    }

    abstract class OperatorPacketPayload(transmission: HexadecimalTransmission): PacketPayload {
        val lengthType = LengthType.fromInt(transmission.consumeInt(1))
        val subPackets = mutableListOf<Packet>()
        init {
            when (lengthType) {
                LENGTH_IN_BITS -> {
                    val lengthInBits = transmission.consumeInt(15)
                    val subTransmission = HexadecimalTransmission(transmission.consumeBits(lengthInBits))
                    while (subTransmission.hasData()) {
                        subPackets.add(Packet(subTransmission))
                    }
                }
                NUMBER_OF_SUB_PACKETS -> {
                    val numSubPackets = transmission.consumeInt(11)
                    repeat(numSubPackets) {
                        subPackets.add(Packet(transmission))
                    }
                }
            }
        }

        abstract override fun getValue(): Long
    }

    class SumPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = subPackets.sumOf { it.getValue() }
    }

    class ProductPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = subPackets.productOf { it.getValue() }
    }

    class MinimumPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = subPackets.minOf { it.getValue() }
    }

    class MaximumPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = subPackets.maxOf { it.getValue() }
    }

    class GreaterThanPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = if (subPackets[0].getValue() > subPackets[1].getValue()) 1 else 0
    }

    class LessThanPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = if (subPackets[0].getValue() < subPackets[1].getValue()) 1 else 0
    }

    class EqualToPacketPayload(transmission: HexadecimalTransmission): OperatorPacketPayload(transmission) {
        override fun getValue(): Long = if (subPackets[0].getValue() == subPackets[1].getValue()) 1 else 0
    }

    class Packet(transmission: HexadecimalTransmission) {
        val header = PacketHeader(transmission)
        val payload: PacketPayload

        init {
            payload = when(header.type) {
                PacketType.SUM -> SumPacketPayload(transmission)
                PacketType.PRODUCT -> ProductPacketPayload(transmission)
                PacketType.MINIMUM -> MinimumPacketPayload(transmission)
                PacketType.MAXIMUM -> MaximumPacketPayload(transmission)
                PacketType.LITERAL -> LiteralPacketPayload(transmission)
                PacketType.GREATER_THAN -> GreaterThanPacketPayload(transmission)
                PacketType.LESS_THAN -> LessThanPacketPayload(transmission)
                PacketType.EQUAL_TO -> EqualToPacketPayload(transmission)
            }
        }

        fun getValue(): Long = payload.getValue()
    }

    fun getSumOfAllVersions(packetHex: String): Int {
        val packet = Packet(HexadecimalTransmission.fromHexString(packetHex))
        return getVersionSum(packet)
    }

    fun getValue(packetHex: String): Long {
        return Packet(HexadecimalTransmission.fromHexString(packetHex)).getValue()
    }

    private fun getVersionSum(packet: Packet): Int {
        if (packet.payload is OperatorPacketPayload) {
            return packet.header.version + packet.payload.subPackets.sumOf { getVersionSum(it) }
        }
        return packet.header.version
    }
}

fun main() {
    val input = readInput("/day16/input.txt")
    println(PacketDecoder().getSumOfAllVersions(input)) // 949
    println(PacketDecoder().getValue(input)) // 1114600142730
}