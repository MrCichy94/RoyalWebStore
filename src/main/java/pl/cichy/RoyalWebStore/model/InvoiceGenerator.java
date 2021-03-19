package pl.cichy.RoyalWebStore.model;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@Component
public class InvoiceGenerator {

    public static class Article {
        int LP;
        String productName;
        BigDecimal quantity;

        @Digits(integer = 8, fraction = 2)
        BigDecimal unitPriceGross;

        @Digits(integer = 8, fraction = 2)
        BigDecimal unitPriceNet;

        @Digits(integer = 8, fraction = 2)
        BigDecimal vat;

        public Article(int LP, String productName, int quantity, BigDecimal unitPriceGross, BigDecimal vat) {
            this.LP = LP;
            this.productName = productName;
            this.quantity = new BigDecimal(quantity);
            this.unitPriceGross = unitPriceGross;
            this.vat = vat;

            BigDecimal point = (BigDecimal.ONE).negate();
            unitPriceNet = unitPriceGross.multiply((point.add(vat))
                    .abs()).setScale(2, RoundingMode.DOWN);
        }
    }

    public static void addTitle(Document layoutDocument) {
        layoutDocument.add(new Paragraph("FAKTURA DETALICZNA")
                .setBold().setUnderline()
                .setTextAlignment(TextAlignment.CENTER));
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
    }

    public static void addInvoiceDetails(Document layoutDocument) {

        Table table = new Table(2, true);

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Sprzedawca").setBold()));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nabywca").setBold()));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nazwa firmy")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nazwa firmy/imie")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nazwaulicy 3, 33-333 Miasto")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nazwaulicy 7, 77-777 Wioska")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("NIP 0000000000")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("NIP 1111111111")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Numer konta")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("48 4444 4444 4444 4444 0000 1111")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        layoutDocument.add(table);
        layoutDocument.add(new Paragraph());

    }

    public static void addPositionsTable(Document layoutDocument, List<Article> articleList) {
        Table table = new Table(UnitValue.createPointArray(new float[]{30f, 150f, 30f, 40f, 65f, 40f, 80f, 85f}));

        // headers
        table.addCell(new Paragraph("Lp").setBold().setTextAlignment(TextAlignment.LEFT));
        table.addCell(new Paragraph("Nazwa").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Jedn").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Ilosc").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Cena netto").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("VAT").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Wartosc netto").setBold().setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Wartosc brutto").setBold().setTextAlignment(TextAlignment.CENTER));

        // items
        for (Article a : articleList) {
            table.addCell(new Paragraph(a.LP + ".").setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Paragraph(a.productName).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Paragraph("szt.").setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Paragraph(a.quantity + "").setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Paragraph(a.unitPriceGross + " zl").setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Paragraph(a.vat + "%").setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Paragraph((a.quantity.multiply(a.unitPriceNet)) + " zl").setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Paragraph((a.quantity.multiply(a.unitPriceGross)) + " zl").setTextAlignment(TextAlignment.RIGHT));
        }

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Paragraph(("SUMA zl"))).setTextAlignment(TextAlignment.RIGHT);
        table.addCell(new Paragraph(("SUMA zl"))).setTextAlignment(TextAlignment.RIGHT);

        layoutDocument.add(table);
    }

    public static void addSummTable(Document layoutDocument) {
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        Table table = new Table(5, true);

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Zaplacono: ")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Do zaplaty: ")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Razem: ")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        layoutDocument.add(table);
    }

    public static void addSign(Document layoutDocument) {

        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());
        layoutDocument.add(new Paragraph());

        Table table = new Table(3, true);

        table.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("Pieczątka i podpis osoby uprawnionej")))
                .setFontSize(9)
                .setTextAlignment(TextAlignment.LEFT);
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Podpis osoby uprawnionej")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("do wystawienia faktury")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("do odbioru faktury")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("_________________________")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("")));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("_________________________")));

        layoutDocument.add(table);
    }

}


