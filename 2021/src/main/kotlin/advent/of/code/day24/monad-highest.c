#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {

    int start = 9;
    if (argc > 1) {
      start = atoi(argv[1]);
    }

    for (int w0 = start; w0 >= 1; w0--) {
      printf("TRYING: %dXXXXXXXXXXXXX\n", w0);

      int x0 = 0;
      int y0 = 0;
      int z0 = 0;
      x0 = x0 * 0;
      x0 = x0 + z0;
      x0 = x0 % 26;
      z0 = z0 / 1;
      x0 = x0 + 11;
      x0 = x0 == w0 ? 1 : 0;
      x0 = x0 == 0 ? 1 : 0;
      y0 = y0 * 0;
      y0 = y0 + 25;
      y0 = y0 * x0;
      y0 = y0 + 1;
      z0 = z0 * y0;
      y0 = y0 * 0;
      y0 = y0 + w0;
      y0 = y0 + 3;
      y0 = y0 * x0;
      z0 = z0 + y0;
      
      for (int w1 = 9; w1 >= 1; w1--) {
        printf("TRYING: %d%dXXXXXXXXXXXX\n", w0, w1);

        int x1 = x0;
        int y1 = y0;
        int z1 = z0;
        x1 = x1 * 0;
        x1 = x1 + z1;
        x1 = x1 % 26;
        z1 = z1 / 1;
        x1 = x1 + 14;
        x1 = x1 == w1 ? 1 : 0;
        x1 = x1 == 0 ? 1 : 0;
        y1 = y1 * 0;
        y1 = y1 + 25;
        y1 = y1 * x1;
        y1 = y1 + 1;
        z1 = z1 * y1;
        y1 = y1 * 0;
        y1 = y1 + w1;
        y1 = y1 + 7;
        y1 = y1 * x1;
        z1 = z1 + y1;
        
        for (int w2 = 9; w2 >= 1; w2--) {
          printf("TRYING: %d%d%dXXXXXXXXXXX\n", w0, w1, w2);

          int x2 = x1;
          int y2 = y1;
          int z2 = z1;
          x2 = x2 * 0;
          x2 = x2 + z2;
          x2 = x2 % 26;
          z2 = z2 / 1;
          x2 = x2 + 13;
          x2 = x2 == w2 ? 1 : 0;
          x2 = x2 == 0 ? 1 : 0;
          y2 = y2 * 0;
          y2 = y2 + 25;
          y2 = y2 * x2;
          y2 = y2 + 1;
          z2 = z2 * y2;
          y2 = y2 * 0;
          y2 = y2 + w2;
          y2 = y2 + 1;
          y2 = y2 * x2;
          z2 = z2 + y2;
          
          for (int w3 = 9; w3 >= 1; w3--) {
            int x3 = x2;
            int y3 = y2;
            int z3 = z2;
            x3 = x3 * 0;
            x3 = x3 + z3;
            x3 = x3 % 26;
            z3 = z3 / 26;
            x3 = x3 + -4;
            x3 = x3 == w3 ? 1 : 0;
            x3 = x3 == 0 ? 1 : 0;
            y3 = y3 * 0;
            y3 = y3 + 25;
            y3 = y3 * x3;
            y3 = y3 + 1;
            z3 = z3 * y3;
            y3 = y3 * 0;
            y3 = y3 + w3;
            y3 = y3 + 6;
            y3 = y3 * x3;
            z3 = z3 + y3;
            
            for (int w4 = 9; w4 >= 1; w4--) {
              int x4 = x3;
              int y4 = y3;
              int z4 = z3;
              x4 = x4 * 0;
              x4 = x4 + z4;
              x4 = x4 % 26;
              z4 = z4 / 1;
              x4 = x4 + 11;
              x4 = x4 == w4 ? 1 : 0;
              x4 = x4 == 0 ? 1 : 0;
              y4 = y4 * 0;
              y4 = y4 + 25;
              y4 = y4 * x4;
              y4 = y4 + 1;
              z4 = z4 * y4;
              y4 = y4 * 0;
              y4 = y4 + w4;
              y4 = y4 + 14;
              y4 = y4 * x4;
              z4 = z4 + y4;
              
              for (int w5 = 9; w5 >= 1; w5--) {
                int x5 = x4;
                int y5 = y4;
                int z5 = z4;
                x5 = x5 * 0;
                x5 = x5 + z5;
                x5 = x5 % 26;
                z5 = z5 / 1;
                x5 = x5 + 10;
                x5 = x5 == w5 ? 1 : 0;
                x5 = x5 == 0 ? 1 : 0;
                y5 = y5 * 0;
                y5 = y5 + 25;
                y5 = y5 * x5;
                y5 = y5 + 1;
                z5 = z5 * y5;
                y5 = y5 * 0;
                y5 = y5 + w5;
                y5 = y5 + 7;
                y5 = y5 * x5;
                z5 = z5 + y5;
                
                for (int w6 = 9; w6 >= 1; w6--) {
                  int x6 = x5;
                  int y6 = y5;
                  int z6 = z5;
                  x6 = x6 * 0;
                  x6 = x6 + z6;
                  x6 = x6 % 26;
                  z6 = z6 / 26;
                  x6 = x6 + -4;
                  x6 = x6 == w6 ? 1 : 0;
                  x6 = x6 == 0 ? 1 : 0;
                  y6 = y6 * 0;
                  y6 = y6 + 25;
                  y6 = y6 * x6;
                  y6 = y6 + 1;
                  z6 = z6 * y6;
                  y6 = y6 * 0;
                  y6 = y6 + w6;
                  y6 = y6 + 9;
                  y6 = y6 * x6;
                  z6 = z6 + y6;
                  
                  for (int w7 = 9; w7 >= 1; w7--) {
                    int x7 = x6;
                    int y7 = y6;
                    int z7 = z6;
                    x7 = x7 * 0;
                    x7 = x7 + z7;
                    x7 = x7 % 26;
                    z7 = z7 / 26;
                    x7 = x7 + -12;
                    x7 = x7 == w7 ? 1 : 0;
                    x7 = x7 == 0 ? 1 : 0;
                    y7 = y7 * 0;
                    y7 = y7 + 25;
                    y7 = y7 * x7;
                    y7 = y7 + 1;
                    z7 = z7 * y7;
                    y7 = y7 * 0;
                    y7 = y7 + w7;
                    y7 = y7 + 9;
                    y7 = y7 * x7;
                    z7 = z7 + y7;
                    
                    for (int w8 = 9; w8 >= 1; w8--) {
                      int x8 = x7;
                      int y8 = y7;
                      int z8 = z7;
                      x8 = x8 * 0;
                      x8 = x8 + z8;
                      x8 = x8 % 26;
                      z8 = z8 / 1;
                      x8 = x8 + 10;
                      x8 = x8 == w8 ? 1 : 0;
                      x8 = x8 == 0 ? 1 : 0;
                      y8 = y8 * 0;
                      y8 = y8 + 25;
                      y8 = y8 * x8;
                      y8 = y8 + 1;
                      z8 = z8 * y8;
                      y8 = y8 * 0;
                      y8 = y8 + w8;
                      y8 = y8 + 6;
                      y8 = y8 * x8;
                      z8 = z8 + y8;
                      
                      for (int w9 = 9; w9 >= 1; w9--) {
                        int x9 = x8;
                        int y9 = y8;
                        int z9 = z8;
                        x9 = x9 * 0;
                        x9 = x9 + z9;
                        x9 = x9 % 26;
                        z9 = z9 / 26;
                        x9 = x9 + -11;
                        x9 = x9 == w9 ? 1 : 0;
                        x9 = x9 == 0 ? 1 : 0;
                        y9 = y9 * 0;
                        y9 = y9 + 25;
                        y9 = y9 * x9;
                        y9 = y9 + 1;
                        z9 = z9 * y9;
                        y9 = y9 * 0;
                        y9 = y9 + w9;
                        y9 = y9 + 4;
                        y9 = y9 * x9;
                        z9 = z9 + y9;
                        
                        for (int w10 = 9; w10 >= 1; w10--) {
                          int x10 = x9;
                          int y10 = y9;
                          int z10 = z9;
                          x10 = x10 * 0;
                          x10 = x10 + z10;
                          x10 = x10 % 26;
                          z10 = z10 / 1;
                          x10 = x10 + 12;
                          x10 = x10 == w10 ? 1 : 0;
                          x10 = x10 == 0 ? 1 : 0;
                          y10 = y10 * 0;
                          y10 = y10 + 25;
                          y10 = y10 * x10;
                          y10 = y10 + 1;
                          z10 = z10 * y10;
                          y10 = y10 * 0;
                          y10 = y10 + w10;
                          y10 = y10 + 0;
                          y10 = y10 * x10;
                          z10 = z10 + y10;
                          
                          for (int w11 = 9; w11 >= 1; w11--) {
                            int x11 = x10;
                            int y11 = y10;
                            int z11 = z10;
                            x11 = x11 * 0;
                            x11 = x11 + z11;
                            x11 = x11 % 26;
                            z11 = z11 / 26;
                            x11 = x11 + -1;
                            x11 = x11 == w11 ? 1 : 0;
                            x11 = x11 == 0 ? 1 : 0;
                            y11 = y11 * 0;
                            y11 = y11 + 25;
                            y11 = y11 * x11;
                            y11 = y11 + 1;
                            z11 = z11 * y11;
                            y11 = y11 * 0;
                            y11 = y11 + w11;
                            y11 = y11 + 7;
                            y11 = y11 * x11;
                            z11 = z11 + y11;
                            
                            for (int w12 = 9; w12 >= 1; w12--) {
                              int x12 = x11;
                              int y12 = y11;
                              int z12 = z11;
                              x12 = x12 * 0;
                              x12 = x12 + z12;
                              x12 = x12 % 26;
                              z12 = z12 / 26;
                              x12 = x12 + 0;
                              x12 = x12 == w12 ? 1 : 0;
                              x12 = x12 == 0 ? 1 : 0;
                              y12 = y12 * 0;
                              y12 = y12 + 25;
                              y12 = y12 * x12;
                              y12 = y12 + 1;
                              z12 = z12 * y12;
                              y12 = y12 * 0;
                              y12 = y12 + w12;
                              y12 = y12 + 12;
                              y12 = y12 * x12;
                              z12 = z12 + y12;
                              
                              for (int w13 = 9; w13 >= 1; w13--) {
                                int x13 = x12;
                                int y13 = y12;
                                int z13 = z12;
                                x13 = x13 * 0;
                                x13 = x13 + z13;
                                x13 = x13 % 26;
                                z13 = z13 / 26;
                                x13 = x13 + -11;
                                x13 = x13 == w13 ? 1 : 0;
                                x13 = x13 == 0 ? 1 : 0;
                                y13 = y13 * 0;
                                y13 = y13 + 25;
                                y13 = y13 * x13;
                                y13 = y13 + 1;
                                z13 = z13 * y13;
                                y13 = y13 * 0;
                                y13 = y13 + w13;
                                y13 = y13 + 1;
                                y13 = y13 * x13;
                                z13 = z13 + y13;

                                if (z13 == 0) {
                                    printf("FOUND VALID VALUE: %d%d%d%d%d%d%d%d%d%d%d%d%d%d\n", w0, w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13);
                                    return 0;
                                }

                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
}