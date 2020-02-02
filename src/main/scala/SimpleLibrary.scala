case class BookData(title: String, year: Int, author: String) // req. 2
case class Book(id: Int, bookData: BookData)
case class LentBook(name: String, book:Book)

object IdGenerator{ //In case multiple SimpleLibraries exist, req. 4
  private var max: Int = 0
  def getNewId: Int = {
    this.max = max + 1
    this.max
  }
}

class SimpleLibrary {
  private var availableBooks: List[Book] = Nil // req. 1
  private var lentBooks: List[LentBook] = Nil
  // req. 5:
  def addBook(bookData: BookData): Unit = this.availableBooks = Book(IdGenerator.getNewId, bookData) :: availableBooks
  def rmBook(id: Int): Unit = this.availableBooks = this.availableBooks.filter(_.id != id) // req. 6
  def listBooks(): Unit ={ // req. 7
    println("---- Available books: ----")
    availableBooks.groupBy(_.bookData).view.mapValues(_.size).foreach(println)
    println("---- Lent books:      ----")
    lentBooks.groupBy(_.book.bookData).view.mapValues(_.size).foreach(println)
    println("--------------------------")
  }
  def searchBook(titlePat: String = ".*"       // req. 8 with assumption - we're only searching available books
                 , yearMin: Int = Int.MinValue // this could be extended to search lent by joining availableBooks
                 , yearMax: Int = Int.MaxValue // with lentBooks mapped to extract Book from each LentBook
                 , authorPat: String =".*"): List[Book] ={
    availableBooks.filter(_.bookData.title.matches(titlePat))
      .filter(_.bookData.author.matches(authorPat))
      .filter(_.bookData.year < yearMax)
      .filter(_.bookData.year > yearMin)
  }
  def lendBook(id: Int, name: String): Unit ={ // req. 9
    val tmpBook = availableBooks.filter(_.id == id)
    if(tmpBook.nonEmpty){
      this.rmBook(id)
      this.lentBooks = LentBook(name, tmpBook.head) :: lentBooks
    }
  }
  def bookDetails(id: Int): Option[Either[Book,LentBook]] = { // req. 10
    val tmpBook = availableBooks.filter(_.id == id)
    if(tmpBook.nonEmpty) Some(Left(tmpBook.head))
    else {
      val tmpBook = lentBooks.filter(_.book.id == id)
      if (tmpBook.nonEmpty) Some(Right(tmpBook.head))
      else None
    }
  }
}

