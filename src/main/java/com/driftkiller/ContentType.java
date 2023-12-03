package com.driftkiller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("unused")
public enum ContentType {
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    TEXT_JAVASCRIPT("text/javascript"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif"),
    AUDIO_MPEG("audio/mpeg"),
    VIDEO_MP4("video/mp4"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private final String value;

    public static ContentType fromMime(String mime)
    {
        for (ContentType contentType : ContentType.values())
            if (contentType.getValue().equals(mime))
                return contentType;
        return null;
    }
}