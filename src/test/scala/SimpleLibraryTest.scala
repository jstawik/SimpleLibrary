import org.scalatest.funsuite.AnyFunSuite

class SimpleLibraryTest extends AnyFunSuite {
 test("Adding and removing and searching books") {
   val lib = new SimpleLibrary
   lib.addBook(BookData("Art of War", -450, "Sun Tzu"))
   lib.addBook(BookData("Maelstrom", 2001, "Peter Watts"))
   lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
   lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
   lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
   lib.rmBook(2)
   lib.rmBook(3)
   assert(lib.searchBook().size == 3)
   assert(lib.searchBook(titlePat = "Maelstrom") == Nil)
   assert(lib.searchBook(authorPat = "Cacao DecoMorreno").size == 2)
 }
  test("Book lending"){
    val lib = new SimpleLibrary
    lib.addBook(BookData("Art of War", -450, "Sun Tzu"))
    lib.addBook(BookData("Maelstrom", 2001, "Peter Watts"))
    lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
    lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
    lib.addBook(BookData("Najwyższa jakość", 2010, "Cacao DecoMorreno"))
    lib.lendBook(IdGenerator.getTopId, "Jakub")
    assert(lib.bookDetails(IdGenerator.getTopId).get.toOption.get.name == "Jakub")
  }
}
