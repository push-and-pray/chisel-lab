import chisel3._

class Count6 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val count = RegInit(0.U(3.W))

  count := Mux(count === 6.U, 0.U, count + 1.U)

  res := count

  // ***** your code ends here *****

  io.dout := res
}
