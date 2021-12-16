package advent.of.code.day16

import advent.of.code.day16.PacketDecoder.*
import advent.of.code.day16.PacketDecoder.LengthType.LENGTH_IN_BITS
import advent.of.code.day16.PacketDecoder.LengthType.NUMBER_OF_SUB_PACKETS
import advent.of.code.day16.PacketDecoder.PacketType.*
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class PacketDecoderTest {

    private val testSubject = PacketDecoder()

    @Test
    fun `test decoding '2FE28'`() {
        val packet = Packet(HexadecimalTransmission.fromHexString("D2FE28"))

        assertThat(packet.header.version).isEqualTo(6)
        assertThat(packet.header.type).isEqualTo(LITERAL)
        assertThat(packet.payload).isInstanceOf(LiteralPacketPayload::class)
        assertThat(packet.payload.getValue()).isEqualTo(2021)
    }

    @Test
    fun `test decoding '38006F45291200'`() {
        val packet = Packet(HexadecimalTransmission.fromHexString("38006F45291200"))

        assertThat(packet.header.version).isEqualTo(1)
        assertThat(packet.header.type).isEqualTo(LESS_THAN)
        assertThat(packet.payload).isInstanceOf(OperatorPacketPayload::class)

        val payload = packet.payload as OperatorPacketPayload
        assertThat(payload.lengthType).isEqualTo(LENGTH_IN_BITS)
        assertThat(payload.subPackets[0].payload.getValue()).isEqualTo(10)
        assertThat(payload.subPackets[1].payload.getValue()).isEqualTo(20)
    }

    @Test
    fun `test decoding 'EE00D40C823060'`() {
        val packet = Packet(HexadecimalTransmission.fromHexString("EE00D40C823060"))

        assertThat(packet.header.version).isEqualTo(7)
        assertThat(packet.header.type).isEqualTo(MAXIMUM)
        assertThat(packet.payload).isInstanceOf(OperatorPacketPayload::class)

        val payload = packet.payload as OperatorPacketPayload
        assertThat(payload.lengthType).isEqualTo(NUMBER_OF_SUB_PACKETS)
        assertThat(payload.subPackets[0].payload.getValue()).isEqualTo(1)
        assertThat(payload.subPackets[1].payload.getValue()).isEqualTo(2)
        assertThat(payload.subPackets[2].payload.getValue()).isEqualTo(3)
    }

    @ParameterizedTest
    @CsvSource(
        "8A004A801A8002F478,16",
        "620080001611562C8802118E34,12",
        "C0015000016115A2E0802F182340,23",
        "A0016C880162017C3686B18A3D4780,31"
    )
    fun `test example inputs for part 1`(hexString: String, expectedVersionSum: String) {
        assertThat(testSubject.getSumOfAllVersions(hexString)).isEqualTo(expectedVersionSum.toInt())
    }

    @ParameterizedTest
    @CsvSource(
        "C200B40A82,3",
        "04005AC33890,54",
        "880086C3E88112,7",
        "CE00C43D881120,9",
        "D8005AC2A8F0,1",
        "F600BC2D8F,0",
        "9C005AC2F8F0,0",
        "9C0141080250320F1802104A08,1",
    )
    fun `test example inputs for part 2`(hexString: String, expectedValue: String) {
        assertThat(testSubject.getValue(hexString)).isEqualTo(expectedValue.toLong())
    }
}