package fr.univrouen.cv24.services;

import fr.univrouen.cv24.entities.*;
import fr.univrouen.cv24.exceptions.InvalidResourceException;
import fr.univrouen.cv24.exceptions.InvalidXMLException;
import fr.univrouen.cv24.repositories.IdentiteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CV24ServiceUnitTest {
    @InjectMocks
    private CV24Service cv24Service;

    @Mock
    private IdentiteRepository identiteRepository;
    @Test
    public void getXMLCVDocumentFromInputStreamReturnSuccessIfInputStreamIsCorrect() {
        //GIVEN
        InputStream stream = getClass().getClassLoader().getResourceAsStream("validCV.xml");
        //WHEN
        Document result;
        try {
            result = cv24Service.getXMLCVDocumentFromInputStream(stream);
        } catch (InvalidXMLException e) {
            throw new RuntimeException(e);
        }
        //THEN
        assertNotNull(result);
        try {
            assert stream != null;
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getXMLCVDocumentFromInputStreamThrow() {
        //GIVEN

        //THEN
        assertThrows(
                InvalidXMLException.class,
                //WHEN
                () -> cv24Service.getXMLCVDocumentFromInputStream(new ByteArrayInputStream("test".getBytes()))
        );

    }

    @Test
    public void isValidCVReturnTrue() {
        //GIVEN
        InputStream stream = getClass().getClassLoader().getResourceAsStream("validCV.xml");
        Document document;
        try {
            document = cv24Service.getXMLCVDocumentFromInputStream(stream);
        } catch (InvalidXMLException e) {
            throw new RuntimeException(e);
        }
        //WHEN
        boolean result;
        try {
            result = cv24Service.isValidCV(document);
        } catch (InvalidResourceException e) {
            throw new RuntimeException(e);
        }

        //THEN
        assertTrue(result);
        try {
            assert stream != null;
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isValidCVReturnFalse() {
        //GIVEN
        InputStream stream = getClass().getClassLoader().getResourceAsStream("invalidCV.xml");
        Document document;
        try {
            document = cv24Service.getXMLCVDocumentFromInputStream(stream);
        } catch (InvalidXMLException e) {
            throw new RuntimeException(e);
        }
        //WHEN
        boolean result;
        try {
            result = cv24Service.isValidCV(document);
        } catch (InvalidResourceException e) {
            throw new RuntimeException(e);
        }

        //THEN
        assertFalse(result);
        try {
            assert stream != null;
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isAlreadyInDatabaseReturnFalse() {
        //GIVEN
        InputStream stream = getClass().getClassLoader().getResourceAsStream("validCV.xml");
        Document document;
        try {
            document = cv24Service.getXMLCVDocumentFromInputStream(stream);
        } catch (InvalidXMLException e) {
            throw new RuntimeException(e);
        }
        Mockito.when(identiteRepository.findByNomAndPrenomAndGenreAndTel(
                "STRING", "string", GenreType.MRS, "+33 0 00 00 00 00"
                )
        ).thenReturn(Optional.empty());
        //WHEN
        boolean result = cv24Service.isAlreadyInDatabase(document);
        //THEN
        assertFalse(result);
    }

    @Test
    public void isAlreadyInDatabaseReturnTrue() {
        //GIVEN
        InputStream stream = getClass().getClassLoader().getResourceAsStream("validCV.xml");
        Document document;
        try {
            document = cv24Service.getXMLCVDocumentFromInputStream(stream);
        } catch (InvalidXMLException e) {
            throw new RuntimeException(e);
        }
        Mockito.when(identiteRepository.findByNomAndPrenomAndGenreAndTel(
                        "STRING", "string", GenreType.MRS, "+33 0 00 00 00 00"
                )
        ).thenReturn(Optional.of(new IdentiteType()));
        //WHEN
        boolean result = cv24Service.isAlreadyInDatabase(document);
        //THEN
        assertTrue(result);
    }
}