import chisel3._

class Accu extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val setZero = Input(Bool())
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****
  val count = RegInit(0.U(8.W))

  when(io.setZero) {
    count := 0.U
  }.otherwise {
    count := count + io.din
  }

  res := count

  // ***** your code ends here *****

  io.dout := res
}
