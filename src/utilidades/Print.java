 package utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtProcessing;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import principais.Cliente;
import principais.Livro;
import principais.Pedido;

public class Print {
	Document document =  null;
	private OutputStream os = null;
	private String PATH = "venda_n";
	private List<Livro> livros = new ArrayList<>();
	
	public static Print print;
	
	public static Print getInstance() {
		if(print == null)
			print = new Print();
		
		return print;
	}
	
	public void printDocument(Pedido pedido) throws Exception, DocumentException {
		try{
			StringBuilder sb_PATH = new StringBuilder();
			sb_PATH.append(PATH);
			sb_PATH.append(pedido.getId());
			sb_PATH.append(".pdf");
			livros.add(new Livro("Brasil"));
			livros.add(new Livro("Argentina"));
			livros.add(new Livro("Bélgica"));
			livros.add(new Livro("Uruguai"));
			livros.add(new Livro("Itália"));
			
			document = new Document(PageSize.A4);
			os = new FileOutputStream(sb_PATH.toString());
			
			PdfWriter.getInstance(document, os);
			
			Font fontSubTittle = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
			Font fontSubTittle2 = new Font(FontFamily.HELVETICA, 10);
			
			document.open();
			
			document.add(tableSpecs(fontSubTittle, fontSubTittle2));
			document.add(tableClientSpecs(fontSubTittle, fontSubTittle2, pedido));
			document.add(tableOrders(fontSubTittle, pedido));
			document.add(tableBooks(livros,fontSubTittle, fontSubTittle2, pedido));
			
			System.out.println("Documento gerado com sucesso!");
		} finally {
			if(document != null)
				document.close();
				
			if(os != null)
				os.close();
		}
	}
	
	private PdfPTable tableSpecs(Font f, Font f2){
		Paragraph specs = new Paragraph("LIC LIVROS \n"
				+ "\n"
				+ "LIC LIVROS IDEIAS CULTURA LTDA - ME"
				+ "\n"
				+ "CPF/CNPJ: 01.289.709/0001-11 \n"
				+ "Inscrição estadual: 85269515", f);
		
		Paragraph adress = new Paragraph("RUA ARACÓIA, 297"
				+ "\n"
				+ "BRÁS DE PINA"
				+ "\n"
				+ "RIO DE JANEIRO - RIO DE JANEIRO - BRASIL"
				+ "\n"
				+ "Telefone: (21) 2290-8264"
				+ "\n"
				+ "E-mail: lic-livros@ig.com.br", f2);
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.addCell(specs);
		table.addCell(adress);
		
		return table;
	}
	
	private PdfPTable tableOrders(Font f, Pedido p){
		Paragraph orderNumber = new Paragraph("PEDIDO NÚMERO: " + p.getId() , f);
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		
		PdfPCell header = new PdfPCell();
		header.setMinimumHeight(30);
		header.setVerticalAlignment(Element.ALIGN_MIDDLE);
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addElement(orderNumber);
		header.setColspan(2);
		table.addCell(header);
		
		return table;
	}
	
	private PdfPTable tableClientSpecs(Font f, Font f2, Pedido p) throws DocumentException{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		Phrase phraseName = new Phrase();
		phraseName.add(new Chunk("Nome: ", f));
		phraseName.add(new Chunk("nome", f2));
		
		Phrase phraseId = new Phrase();
		phraseId.add(new Chunk("ID: ", f));
		phraseId.add(new Chunk("id", f2));
		
		Phrase phraseAdress = new Phrase();
		phraseAdress.add(new Chunk("Endereço: ", f));
		phraseAdress.add(new Chunk("endereço", f2));
		
		Phrase phrasePhone = new Phrase();
		phrasePhone.add(new Chunk("Telefone: ", f));
		phrasePhone.add(new Chunk("telefone", f2));
		
		Phrase phraseAdressAddOn = new Phrase();
		phraseAdressAddOn.add(new Chunk("Complemento: ", f));
		phraseAdressAddOn.add(new Chunk("complemento", f2));
		
		Phrase phraseNeighboor = new Phrase();
		phraseNeighboor.add(new Chunk("Bairro: ", f));
		phraseNeighboor.add(new Chunk("bairro", f2));
		
		Phrase phraseObs = new Phrase();
		phraseObs.add(new Chunk("Observação: ", f));
		phraseObs.add(new Chunk("observacao", f2));
		
		Phrase phraseDate = new Phrase();
		phraseDate.add(new Chunk("Data: ", f));
		phraseDate.add(new Chunk(dateFormat.format(cal.getTime()), f2));

		
		PdfPTable table = new PdfPTable(2);
		PdfPCell cellName = new PdfPCell(phraseName);
		PdfPCell cellID = new PdfPCell(phraseId);
		PdfPCell cellAdress = new PdfPCell(phraseAdress);
		PdfPCell cellPhone = new PdfPCell(phrasePhone);
		PdfPCell cellAdressAddOn = new PdfPCell(phraseAdressAddOn);
		PdfPCell cellNeighboor = new PdfPCell(phraseNeighboor);
		PdfPCell cellObs = new PdfPCell(phraseObs);
		PdfPCell cellDate = new PdfPCell(phraseDate);
		
		table.addCell(cellName);
		table.addCell(cellID);
		table.addCell(cellAdress);
		table.addCell(cellPhone);
		table.addCell(cellAdressAddOn);
		table.addCell(cellNeighboor);
		table.addCell(cellObs);
		table.addCell(cellDate);
		table.setWidthPercentage(100f);
		return table;
	}
	
	private PdfPTable tableBooks(List<Livro> list, Font f, Font f2, Pedido p){
		
		PdfPTable table = new PdfPTable(new float[] {1.5f, 4, 1.5f, 1, 1, 1});
		table.setWidthPercentage(100f);
		PdfPCell cellReferencia = new PdfPCell(new Phrase(new Chunk("Referência", f)));
		PdfPCell cellDescrição = new PdfPCell(new Phrase(new Chunk("Descrição", f)));
		PdfPCell cellQuantidade = new PdfPCell(new Phrase(new Chunk("Quantidade", f)));
		PdfPCell cellPreco = new PdfPCell(new Phrase(new Chunk("Preço", f)));
		PdfPCell cellDesconto = new PdfPCell(new Phrase(new Chunk("Desconto", f)));
		PdfPCell cellTotal = new PdfPCell(new Phrase(new Chunk("Total", f)));
		
		table.addCell(cellReferencia);
		table.addCell(cellDescrição);
		table.addCell(cellQuantidade); 
		table.addCell(cellPreco);
		table.addCell(cellDesconto);
		table.addCell(cellTotal);
		
		for(int i = 0; i < p.getPacote().getLivros().size(); i++) {
			table.addCell(new PdfPCell(new Phrase(p.getPacote().getLivros().get(i).getEditora())));
			table.addCell(new PdfPCell(new Phrase(p.getPacote().getLivros().get(i).getNome())));
			table.addCell(new PdfPCell(new Phrase(p.getPacote().getLivros().get(i).getQuantidade())));
			table.addCell(new PdfPCell(new Phrase((float)p.getPacote().getLivros().get(i).getPreco())));
			table.addCell(new PdfPCell(new Phrase("X%")));
			table.addCell(new PdfPCell(new Phrase("preco - desconto")));
		}
		
		return table;
	}
}
