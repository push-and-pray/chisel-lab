import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class Mux5Test extends AnyFlatSpec with ChiselScalatestTester {
  "Mux5 " should "pass" in {
    test(new Mux5()) { dut =>
      dut.io.a.poke(4.U)
      dut.io.b.poke(3.U)
      dut.io.c.poke(2.U)
      dut.io.d.poke(1.U)
      dut.io.e.poke(0.U)

      dut.io.sel.poke(0.U)
      dut.clock.step()
      dut.io.y.expect(4.U)

      dut.io.sel.poke(1.U)
      dut.clock.step()
      dut.io.y.expect(3.U)

      dut.io.sel.poke(2.U)
      dut.clock.step()
      dut.io.y.expect(2.U)

      dut.io.sel.poke(3.U)
      dut.clock.step()
      dut.io.y.expect(1.U)

      dut.io.sel.poke(4.U)
      dut.clock.step()
      dut.io.y.expect(0.U)
    }
  }
}
