package dtos;

import lombok.Data;

@Data
public class TronaldDumpDTO {
    private String value;
    private Object _links;
    private String href;

    public void setHref() {
        String links = get_links().toString();
        this.href = links.substring(links.lastIndexOf("=") + 1, links.indexOf("}"));
        this._links = null;
    }
}