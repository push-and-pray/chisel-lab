import heap._
import Heap.Operation
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class HeapTest extends AnyFlatSpec with ChiselScalatestTester {

  behavior of "Heap"

  it should "present the currently largest number on io.root while ready and not empty" in {
    test(new TestHeap) { dut =>
      for (a <- 1 to 8) {
        dut.io.op.poke(Operation.Insert)
        dut.io.newValue.poke(a.U)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
        dut.io.root.expect(a.U)
      }

      for (a <- 8 to 1) {
        dut.io.op.poke(Operation.RemoveRoot)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
        dut.io.root.expect(a.U)
      }
    }
  }

  it should "assert empty after all numbers have been removed" in {
    test(new TestHeap) { dut =>
      // new heap is empty
      dut.io.empty.expect(true.B)

      // fill up heap
      for (a <- 1 to 8) {
        dut.io.op.poke(Operation.Insert)
        dut.io.newValue.poke(a.U)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
        dut.io.empty.expect(false.B)
      }

      // empty heap
      for (a <- 1 to 8) {
        dut.io.op.poke(Operation.RemoveRoot)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }

      dut.io.empty.expect(true.B)

    }
  }

  it should "assert full when 8 numbers have been inserted" in {
    test(new TestHeap) { dut =>
      // fill up heap
      for (a <- 1 to 7) {
        dut.io.op.poke(Operation.Insert)
        dut.io.newValue.poke(a.U)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
        dut.io.full.expect(false.B)
      }

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(8.U)
      dut.io.valid.poke(1.B)
      dut.clock.step()
      dut.io.valid.poke(0.B)
      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      dut.io.full.expect(true.B)
    }
  }

  it should "deassert full after one number is removed when it was full" in {
    test(new TestHeap) { dut =>
      // fill up heap
      for (a <- 1 to 8) {
        dut.io.op.poke(Operation.Insert)
        dut.io.newValue.poke(a.U)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      // remove val
      dut.io.full.expect(true.B)
      dut.io.op.poke(Operation.RemoveRoot)
      dut.io.valid.poke(1.B)
      dut.clock.step()
      dut.io.valid.poke(0.B)
      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      dut.io.full.expect(false.B)

    }
  }

  it should "not change the sequence if new insertions are issued when it is full" in {
    test(new TestHeap) { dut =>
      // fill up heap
      for (a <- 1 to 8) {
        dut.io.op.poke(Operation.Insert)
        dut.io.newValue.poke(a.U)
        dut.io.valid.poke(1.B)
        dut.clock.step()
        dut.io.valid.poke(0.B)
        while (!dut.io.ready.peekBoolean()) dut.clock.step()
      }
      dut.io.full.expect(true.B)
      dut.io.root.expect(8)

      dut.io.op.poke(Operation.Insert)
      dut.io.newValue.poke(9.U)
      dut.io.valid.poke(1.B)
      dut.clock.step()
      dut.io.valid.poke(0.B)
      while (!dut.io.ready.peekBoolean()) dut.clock.step()
      dut.io.full.expect(true.B)
      dut.io.root.expect(8)

    }
  }

}
