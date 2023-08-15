package com.driftkiller;

import lombok.AllArgsConstructor;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * The FormDataItem class is a wrapper for Apache Commons FileUpload's FileItem class,
 * providing convenient methods to access file data.
 * It allows users to interact with file items without directly using the FileItem class.
 */
@AllArgsConstructor
public class FormDataItem {

    private FileItem fileItem;

    /**
     * Returns the content type of the file item.
     *
     * @return The content type as a ContentType object.
     */
    public ContentType getContentType()
    {
        return ContentType.fromMime(fileItem.getContentType());
    }

    /**
     * Returns an InputStream object for reading the contents of the file item.
     *
     * @return An InputStream object representing the file's contents.
     * @throws IOException if an I/O error occurs while creating the input stream.
     */
    public InputStream asInputStream() throws IOException {
        return fileItem.getInputStream();
    }

    /**
     * Returns the name of the file associated with the file item.
     * This may be null if they provided something that wasn't uploaded, like text/json.
     *
     * @return The name of the file.
     */
    public String getName()
    {
        return fileItem.getName();
    }

    /**
     * Returns the name of the form field that the file item corresponds to.
     *
     * @return The name of the form field.
     */
    public String getFieldName()
    {
        return fileItem.getFieldName();
    }

    /**
     * Returns the contents of the file item as a String.
     *
     * @return The contents of the file item as a String.
     */
    public String getString()
    {
        return fileItem.getString();
    }

    /**
     * Returns the contents of the file item as a String, using the specified character encoding.
     *
     * @param encoding The character encoding to use.
     * @return The contents of the file item as a String.
     * @throws UnsupportedEncodingException if the specified encoding is not supported.
     */
    public String getString(String encoding) throws UnsupportedEncodingException {
        return fileItem.getString(encoding);
    }

    /**
     * Returns the contents of the file item as a byte array.
     *
     * @return The contents of the file item as a byte array.
     */
    public byte[] getBinary()
    {
        return fileItem.get();
    }


}
