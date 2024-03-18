package com.BusBooking.service;

import com.BusBooking.entity.Passenger;
import com.BusBooking.repository.BusRepository;
import com.BusBooking.repository.PassengerRepository;
import com.BusBooking.repository.RouteRepository;
import com.BusBooking.repository.SubRouteRepository;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.BusBooking.util.ExcelGeneratorService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.Properties;

@Service
public class PassengerService {


    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SubRouteRepository subRouteRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public void savePassengerDetails(Passenger passenger, long busId, long routeId, long subRouteId) {
        boolean busAndRouteExists = isEntityPresent(busId, busRepository) && isEntityPresent(routeId, routeRepository);
        boolean busAndSubRouteExists = isEntityPresent(busId, busRepository) && isEntityPresent(subRouteId, subRouteRepository);

        if (busAndRouteExists || busAndSubRouteExists) {
            // Either bus and route exist or bus and subroute exist, save passenger details
            Passenger savedPassenger = passengerRepository.save(passenger);
            // Generate PDF
            byte[] pdfBytes = generatePdf(savedPassenger, "From Location", "To Location", "Date");
            // Send email with PDF attachment
            sendEmailWithAttachment(savedPassenger.getEmail(), "Booking Confirmation", "Dear Passenger, Your booking is confirmed.", pdfBytes);
        } else {
            // Neither bus and route nor bus and subroute exist
            throw new IllegalArgumentException("Neither bus and route nor bus and subroute exist.");
        }
    }

    private <T> boolean isEntityPresent(long id, JpaRepository<T, Long> repository) {
        Optional<T> entity = repository.findById(id);
        return entity.isPresent();
    }

    private byte[] generatePdf(Passenger passenger, String fromLocation, String toLocation, String date) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Passenger Name: " + passenger.getFirstName());
                contentStream.newLine();
                contentStream.showText("Passenger Name: " + passenger.getLastName());
                contentStream.newLine();
                contentStream.showText("Email: " + passenger.getEmail());
                contentStream.newLine();
                contentStream.showText("Mobile: " + passenger.getMobile());
                contentStream.newLine();
                contentStream.showText((fromLocation));
                contentStream.newLine();
                contentStream.showText((toLocation));
                contentStream.newLine();
                contentStream.showText((date));
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] attachment) {
        // You need to replace these with your email credentials
        final String username = "swaruprudrapaul5@gmail.com";
        final String password = "koapedbpqaghxdga";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachment != null) {
                messageBodyPart = new MimeBodyPart();
                ByteArrayDataSource source = new ByteArrayDataSource(attachment, "application/pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("BookingConfirmation.pdf");
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}