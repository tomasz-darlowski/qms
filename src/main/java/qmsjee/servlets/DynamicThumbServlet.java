/*
 
 
 */
package qmsjee.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import qmsjee.entities.entity.FileEntity;
import qmsjee.services.entityServices.interfaces.IFileService;

/**
 *
 * @author darlotom
 */
public class DynamicThumbServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    // Statics ------------------------------------------------------------------------------------
    @Inject
    private IFileService fileService;
    private static final long serialVersionUID = -5630892684754926491L;

    // Actions ------------------------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get ID from request.
        String imageUid = request.getParameter("uid");

        // Check if ID is supplied to the request.
        if (imageUid == null) {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Lookup Image by ImageId in database.
        // Do your "SELECT * FROM Image WHERE ImageID" thing.
        FileEntity image = fileService.findByUid(imageUid);

        // Check if image is actually retrieved from database.
        if (image == null) {
            // Do your thing if the image does not exist in database.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        
        byte[] thumb = createThumbnail(image.getContent(), 100);

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(image.getMimeType());
        response.setContentLength(thumb.length);
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            output.write(thumb);
        } finally {
            // Gently close streams.
            close(output);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

    public static byte[] createThumbnail(byte[] orig, int maxDim) {

        try {
            ImageIcon imageIcon = new ImageIcon(orig);
            Image inImage = imageIcon.getImage();
            double scale = (double) maxDim / (double) inImage.getHeight(null);

            int scaledW = (int) (scale * inImage.getWidth(null));
            int scaledH = (int) (scale * inImage.getHeight(null));

            BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
            AffineTransform tx = new AffineTransform();

            if (scale < 1.0d) {
                tx.scale(scale, scale);
            }

            Graphics2D g2d = outImage.createGraphics();
            g2d.drawImage(inImage, tx, null);
            g2d.dispose();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(outImage, "JPG", baos);
            byte[] bytesOut = baos.toByteArray();

            return bytesOut;
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }
}