import chisel3._

class Count15 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val count = RegInit(0.U(4.W))

  count := Mux(count === 15.U, 0.U, count + 1.U)

  res := count

  // ***** your code ends here *****

  io.dout := res
}
