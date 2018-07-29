package com.peter2.ejb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peter2.ejb.entity.Book;
import com.peter2.ejb.entity.BookEntity;
import com.peter2.ejb.entity.BorrowNote;
import com.peter2.ejb.entity.Reader;
import com.peter2.ejb.service.IBookTestService;

/**
 * Servlet implementation class MySimpleServlet
 */
@WebServlet("/EJBDemoServlet")
public class EJBDemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(beanName="SuperUserService")
	private IBookTestService service; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EJBDemoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		out.print("<html><head><title>Simple View</title></head><body><div>");
	
		/**
		out.print(service.getBook("9789861250557")+"<br/>");
		BookEntity be = service.getBE(42); 
		out.print(be.getBook().getIsbn()+"<br/>");
		/**/

		/**
		Book book = new Book();
		book.setIsbn("123");
		book.setName("1");
		srv.addBook(book);
		/**/

		/**/
		List<?> readerList = service.listReaders();
		out.print("<table border=\"1\">");
		out.print("<tr><td>ID</td><td>NAME</td><td>BORROW QTY</td><td>RESERVE QTY</td></tr>");
		for (Object reader_ : readerList) {
			Reader reader = (Reader) reader_;
			out.print("<tr><td>" + reader.getId() + "</td><td>" + reader.getName() + 
					"</td><td>" + reader.getBorrowQty() + "</td><td>" + reader.getReserveQty() + "</td></tr>");
		}
		out.print("</table>");
		/**/

		/**
		List<BookEntity> beList = service.listBookEntities();
		out.print("<table border=\"1\">");
		out.print("<tr><td>ID</td><td>LOCATION</td><td>ISBN</td><td>BOOK NAME</td><td>BORROWER</td></tr>");
		for (BookEntity be : beList) {
			Book book = be.getBook();
			String readerName = (be.getBorrowing()==null) ? ("") : (be.getBorrowing().getReader().getName());
			out.print("<tr><td>" + be.getId() + "</td><td>" + be.getLocation() + 
					"</td><td>" + book.getIsbn() + "</td><td>" + book.getName() + 
					"</td><td>" + readerName + "</td></tr>");
		}
		out.print("</table>");
		/**/
		
		/**/
		List<?> bookList = service.listBooks();
		out.print("<table border=\"1\">");
		out.print("<tr><td>ISBN</td><td>BOOK NAME</td><td>TYPE</td><td>##</td><td>B#</td><td>O#</td><td>R#</td><td>BE_ID</td><td>LOCATION</td><td>BORROWER</td></tr>");
		
		for (Object book_ : bookList) {
			Book book = (Book) book_;
			out.print("<tr><td rowspan=\""+(((Book) book).getEntities().size()+1)+"\">"+book.getIsbn()+"</td><td>"+book.getName()+
					"</td><td>"+book.getType()+"</td><td>"+book.getQty()+
					"</td><td>"+book.getBorrowQty()+"</td><td>"+book.getOndeskQty()+
					"</td><td>"+book.getReserveQty()+ 
					"</td></tr>");
			for (BookEntity be : book.getEntities()) {
				String readerName = (be.getBorrowNote()==null) ? ("") : (((Reader) ((BorrowNote) be.getBorrowNote()).getReader()).getName());
				out.print("<tr><td>"+ "</td><td></td><td></td><td></td><td></td><td></td><td>" + 
						be.getId()+"</td><td>"+be.getLocation()+
						"</td><td>"+readerName+
						"</td></tr>");
			}
		}
		out.print("</table>");
		/**/
		out.print("</div></body></html>");
	}
}
