import chisel3._

class Delay extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val reg1 = RegNext(io.din)
  val reg2 = RegNext(reg1)
  res := reg2

  // ***** your code ends here *****

  io.dout := res
}
