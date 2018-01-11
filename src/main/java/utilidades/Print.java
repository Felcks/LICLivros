 package utilidades;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtProcessing;
import javax.swing.JOptionPane;

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
import principais.EditoraManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pedido;
import principais.TipoPedido;

public class Print {
	Document document =  null;
	private OutputStream os = null;
	private String PATH = "PEDIDOS/";
	private String PATH_RELATORIO = "RELATÓRIOS/";
	private List<Livro> livros = new ArrayList<Livro>();
	private double valorTotal = 0;
	
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
			sb_PATH.append(pedido.getId() + " - " + pedido.getCliente().getNome());
			sb_PATH.append(".pdf");
			document = new Document(PageSize.A4);
			os = new FileOutputStream(sb_PATH.toString());
			
			PdfWriter.getInstance(document, os);
			
			Font fontSubTittle = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
			Font fontSubTittle2 = new Font(FontFamily.HELVETICA, 10);
			Font fontSubTittle3 = new Font(FontFamily.HELVETICA, 7);
			
			document.open();
			
			document.add(tableSpecs(fontSubTittle, fontSubTittle2));
			document.add(tableClientSpecs(fontSubTittle, fontSubTittle2, pedido));
			document.add(tableOrders(pedido));
			document.add(tableBooks(livros,fontSubTittle, fontSubTittle2, fontSubTittle3, pedido));
			document.add(tableTotal(valorTotal));
			
			Desktop.getDesktop().open(new File(sb_PATH.toString()));
			//System.out.println("Documento gerado com sucesso!");
		} finally {
			if(document != null)
				document.close();
				
			if(os != null)
				os.close();
		}
	}
	
	public void printRelatorio(String parcialOuFinal) throws DocumentException, IOException{
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Calendar cal = Calendar.getInstance();
			
			StringBuilder sb_PATH = new StringBuilder();
			sb_PATH.append(PATH_RELATORIO);
			sb_PATH.append(dateFormat.format(cal.getTime()).toString());
			sb_PATH.append(parcialOuFinal);
			sb_PATH.append(".pdf");
			
			for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
				String editoraName = EditoraManager.getInstance().getEditoras().get(i).getNome();
				for(int j = 0; j < EstoqueManager.getInstance().getLivrosDeUmaEditora(editoraName).size(); j++){
					livros.add(EstoqueManager.getInstance().getLivrosDeUmaEditora(editoraName).get(j));
				}
			}
		
		document = new Document(PageSize.A4);
		os = new FileOutputStream(sb_PATH.toString());
		
		PdfWriter.getInstance(document, os);

		Font fontSubTittle = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
		Font fontSubTittle2 = new Font(FontFamily.HELVETICA, 10);
		Font fontSubTittle3 = new Font(FontFamily.HELVETICA, 7);
		
		
		document.open();
		
		document.add(tableSpecs(fontSubTittle, fontSubTittle2));
		document.add(tableTitle(parcialOuFinal));
		document.add(tableRelatorio(livros, fontSubTittle, fontSubTittle2));
		livros.clear();
		if(parcialOuFinal.contains("FINAL"))
			EstoqueManager.getInstance().limparAposRelatorioFinal();
		
		Desktop.getDesktop().open(new File(sb_PATH.toString()));
		
		
		} finally {
			if(document != null)
				document.close();
				
			if(os != null)
				os.close();
		}
		
	}
	
	private PdfPTable tableRelatorio(List<Livro> l, Font f, Font f2){
		PdfPTable table = new PdfPTable(new float[] { 4, 2, 1.1F, 1.1F, 1.1F, 1.1F});
		table.setWidthPercentage(100f);
		
		l.sort(((Comparator)new Livro("")));
		
		//PdfPCell cellID = new PdfPCell(new Phrase(new Chunk("ID", f)));
		PdfPCell cellNome= new PdfPCell(new Phrase(new Chunk("Nome", f)));
		PdfPCell celEditora = new PdfPCell(new Phrase(new Chunk("Editora", f)));
		PdfPCell cellQuantidade = new PdfPCell(new Phrase(new Chunk("Qtd", f)));
		PdfPCell cellComprar = new PdfPCell(new Phrase(new Chunk("Comprar", f)));
		PdfPCell cellVendidos= new PdfPCell(new Phrase(new Chunk("Vendidos", f)));
		PdfPCell cellPreco = new PdfPCell(new Phrase(new Chunk("Preço", f)));
		
		//table.addCell(cellID);
		table.addCell(cellNome);
		table.addCell(celEditora); 
		table.addCell(cellQuantidade);
		table.addCell(cellComprar);
		table.addCell(cellVendidos);
		table.addCell(cellPreco);
		
		for(int i = 0; i < l.size(); i++) {
			//table.addCell(new PdfPCell(new Phrase(String.valueOf(l.get(i).getId()))));
			if(l.get(i).getComprar() == 0)
				continue;
			
			table.addCell(new PdfPCell(new Phrase(l.get(i).getNome(), f2)));
			table.addCell(new PdfPCell(new Phrase(l.get(i).getEditora(), f2)));
			table.addCell(new PdfPCell(new Phrase(String.valueOf(l.get(i).getQuantidade()),f2)));
			table.addCell(new PdfPCell(new Phrase(String.valueOf(l.get(i).getComprar()), f2)));
			table.addCell(new PdfPCell(new Phrase(String.valueOf(l.get(i).getVendidos()), f2)));
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String precoFormatado = nf.format (l.get(i).getPreco());
			table.addCell(new PdfPCell(new Phrase(precoFormatado, f2)));
		}
		
		return table;
	} 
	
	private PdfPTable tableSpecs(Font f, Font f2){
		Paragraph specs = new Paragraph("LIC LIVROS \n"
				+ "\n"
				+ "LIC LIVROS IDEIAS CULTURA LTDA - ME"
				+ "\n"
				+ "CPF/CNPJ: 01.289.709/0001-11 \n"
				+ "Inscrição estadual: 85269515", f);
		
		Paragraph adress = new Paragraph("RUA BRAGA, 106"
				+ "\n"
				+ "PENHA CIRCULAR"
				+ "\n"
				+ "RIO DE JANEIRO - RIO DE JANEIRO - BRASIL"
				+ "\n"
				+ "Telefone: (21) 2290-8264"
				+ "\n"
				+ "E-mail: liclivros@gmail.com", f2);
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.addCell(specs);
		table.addCell(adress);
		
		return table;
	}
	
	private PdfPTable tableOrders(Pedido p){
		Paragraph orderNumber = new Paragraph("PEDIDO NÚMERO: " + p.getId() , new Font(FontFamily.HELVETICA, 14, Font.BOLD));
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		
		PdfPCell header = new PdfPCell();
		header.setMinimumHeight(30);
		header.setVerticalAlignment(Element.ALIGN_CENTER);
		header.setHorizontalAlignment(Element.ALIGN_CENTER);
		header.addElement(orderNumber);
		header.setColspan(2);
		table.addCell(header);
		
		return table;
	}
	
	private PdfPTable tableTitle(String parcialOuFinal){
		String tipo = "";
		if(parcialOuFinal.contains("parcial"))
			tipo = "PARCIAL";
		else
			tipo = "FINAL";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		Paragraph orderNumber = new Paragraph("RELATÓRIO " + tipo + ": " + dateFormat.format(cal.getTime()).toString() , new Font(FontFamily.HELVETICA, 14, Font.BOLD));
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		
		PdfPCell header = new PdfPCell();
		header.setMinimumHeight(30);
		header.setVerticalAlignment(Element.ALIGN_CENTER);
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
		phraseName.add(new Chunk(p.getCliente().getNome(), f2));
		
		Phrase phraseId = new Phrase();
		phraseId.add(new Chunk("ID: ", f));
		phraseId.add(new Chunk(String.valueOf(p.getCliente().getId()), f2));
		
		Phrase phraseAdress = new Phrase();
		phraseAdress.add(new Chunk("Rua: ", f));
		phraseAdress.add(new Chunk(p.getCliente().getRua(), f2));
		
		Phrase phrasePhone = new Phrase();
		phrasePhone.add(new Chunk("Telefone: ", f));
		phrasePhone.add(new Chunk(p.getCliente().getTelefone() + " / " + p.getCliente().getCelular(), f2));
		
		Phrase phraseAdressAddOn = new Phrase();
		phraseAdressAddOn.add(new Chunk("Complemento: ", f));
		phraseAdressAddOn.add(new Chunk(p.getCliente().getComplemento(), f2));
		
		Phrase phraseNeighboor = new Phrase();
		phraseNeighboor.add(new Chunk("Bairro: ", f));
		phraseNeighboor.add(new Chunk(p.getCliente().getBairro(), f2));
	 	
		Phrase pharesePag = new Phrase();
		pharesePag.add(new Chunk("Pagamento: ", f));
		pharesePag.add(new Chunk( p.getFormaDePagamento().getNome(), f2));
		
		Phrase phraseDate = new Phrase();
		phraseDate.add(new Chunk("Data: ", f));
		phraseDate.add(new Chunk(dateFormat.format(cal.getTime()), f2));
		
		Phrase phraseSchool = new Phrase();
		phraseSchool.add(new Chunk("Escola: ", f));
		if(p.getPacote().getEscola() != null)
			phraseSchool.add(new Chunk(p.getPacote().getEscola().getNome() + " - " + p.getPacote().getAnoEscolar().getNome(), f2));
		else
			phraseSchool.add(new Chunk("   ---   ", f2));
		
		Phrase phraseObs = new Phrase();
		phraseObs.add(new Chunk("Obs: ", f));
		phraseObs.add(new Chunk(p.getObs(), f2));
		
		Phrase tipo = new Phrase();
		tipo.add(new Chunk("Tipo: ", f));
		tipo.add(new Chunk(p.getTipoPedido().toString(), f2));
		
		PdfPTable table = new PdfPTable(2);
		PdfPCell cellName = new PdfPCell(phraseName);
		PdfPCell cellID = new PdfPCell(phraseId);
		PdfPCell cellAdress = new PdfPCell(phraseAdress);
		PdfPCell cellPhone = new PdfPCell(phrasePhone);
		PdfPCell cellAdressAddOn = new PdfPCell(phraseAdressAddOn);
		PdfPCell cellNeighboor = new PdfPCell(phraseNeighboor);
		PdfPCell cellPag = new PdfPCell(pharesePag);
		PdfPCell cellDate = new PdfPCell(phraseDate);
		PdfPCell cellSchool = new PdfPCell(phraseSchool);
		PdfPCell cellObs = new PdfPCell(phraseObs);
		PdfPCell cellTipo = new PdfPCell(tipo);
		
		table.addCell(cellName);
		table.addCell(cellPhone);
		table.addCell(cellAdress);
		table.addCell(cellNeighboor);
		table.addCell(cellAdressAddOn);
		table.addCell(cellDate);
		table.addCell(cellPag);
		table.addCell(cellSchool);
		table.addCell(cellTipo);
		table.addCell(cellObs);
		table.setWidthPercentage(100f);
		return table;
	}
	
	private PdfPTable tableBooks(List<Livro> list, Font f, Font f2, Font f3, Pedido p){
		
		PdfPTable table = new PdfPTable(new float[] {1.5f, 4.5f, 0.75f, 1.25f, 1, 1.25f});
		table.setWidthPercentage(100f);
		PdfPCell cellReferencia = new PdfPCell(new Phrase(new Chunk("Editora", f)));
		PdfPCell cellDescrição = new PdfPCell(new Phrase(new Chunk("Livro", f)));
		PdfPCell cellQuantidade = new PdfPCell(new Phrase(new Chunk("Qtd", f)));
		PdfPCell cellPreco = new PdfPCell(new Phrase(new Chunk("Preço", f)));
		PdfPCell cellDesconto = new PdfPCell(new Phrase(new Chunk("Desconto", f)));
		PdfPCell cellTotal = new PdfPCell(new Phrase(new Chunk("Total", f)));
		
		table.addCell(cellReferencia);
		table.addCell(cellDescrição);
		table.addCell(cellQuantidade); 
		table.addCell(cellPreco);
		table.addCell(cellDesconto);
		table.addCell(cellTotal);
		
		if(p.getTipoPedido() == TipoPedido.NORMAL){
			for(int i = 0; i < p.getIdsDosLivrosComprados().length; i++){
				int id = p.getIdsDosLivrosComprados()[i];
				if(id >= 0){
					double total = p.getPacote().getLivros().get(i).getPreco() * p.getQtdDosLivrosComprados()[i];
					double desconto =  (((double)p.getDesconto()) / 100.0) * total;
					double comDesconto = total - desconto;
					
					table.addCell(new PdfPCell(new Phrase(p.getPacote().getLivros().get(i).getEditora(), f2)));
					table.addCell(new PdfPCell(new Phrase(p.getPacote().getLivros().get(i).getNome(), f2)));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getQtdDosLivrosComprados()[i]), f2)));
					
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String precoFormatado = nf.format (p.getPacote().getLivros().get(i).getPreco());
					table.addCell(new PdfPCell(new Phrase(precoFormatado, f2)));
					
					table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getDesconto()+ "%"), f2)));
					
					String totalFormatado = nf.format(comDesconto);
					table.addCell(new PdfPCell(new Phrase(totalFormatado, f2)));
					valorTotal = valorTotal + comDesconto;
				}
			}
		}
		else
		{
			for(int i = 0; i < p.getIdsDosLivrosComprados().length; i++){
				int id = p.getIdsDosLivrosComprados()[i];
				if(id >= 0){
					double total = EstoqueManager.getInstance().getLivroPeloId(id).getPreco() * p.getQtdDosLivrosComprados()[i];
					double desconto =  (((double)p.getDesconto()) / 100.0) * total;
					double comDesconto = total - desconto;
					
					Livro livro = EstoqueManager.getInstance().getLivroPeloId(id);
					float novoPreco = (float) ((livro.getPreco() * p.getDesconto()) / 100);
					novoPreco = (float)livro.getPreco() - novoPreco;
					table.addCell(new PdfPCell(new Phrase(livro.getEditora(), f2)));
					table.addCell(new PdfPCell(new Phrase(livro.getNome(), f2)));
					table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getQtdDosLivrosComprados()[i]), f2)));
					
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String precoFormatado = nf.format (livro.getPreco());
					table.addCell(new PdfPCell(new Phrase(precoFormatado, f2)));
					
					table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getDesconto()+ "%"), f2)));
					
					String totalFormatado = nf.format(comDesconto);
					table.addCell(new PdfPCell(new Phrase(totalFormatado, f2)));
					valorTotal = valorTotal + comDesconto;
				}
			}
		}
		
		return table;
	}
	private PdfPTable tableTotal(double total){
		PdfPTable table = new PdfPTable(1);
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String totalFormatado = nf.format (total);
		table.addCell(new Phrase(new Chunk("Total: " + totalFormatado)));
		table.setWidthPercentage(100f);
		valorTotal = 0;
		return table;
	}
}
