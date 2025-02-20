import chisel3._

/** Use Mux2 components to build a 4:1 multiplexer
  */

class Mux4 extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val c = Input(UInt(1.W))
    val d = Input(UInt(1.W))
    val sel = Input(UInt(2.W))
    val y = Output(UInt(1.W))
  })

  // ***** your code starts here *****

  // create a Mux4 component out of Mux2 components
  // and connect the input and output ports.

  val mux1 = Module(new Mux2())

  val mux2_1 = Module(new Mux2())
  val mux2_2 = Module(new Mux2())

  mux2_1.io.a := io.a
  mux2_1.io.b := io.b

  mux2_2.io.a := io.c
  mux2_2.io.b := io.d

  mux1.io.a := mux2_1.io.y
  mux1.io.b := mux2_2.io.y

  io.y := mux1.io.y

  mux1.io.sel := io.sel(1)

  mux2_1.io.sel := io.sel(0)
  mux2_2.io.sel := io.sel(0)

  // below is dummy code to make this example compile

  // ***** your code ends here *****
}
