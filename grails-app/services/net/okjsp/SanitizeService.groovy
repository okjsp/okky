package net.okjsp

import org.owasp.html.AttributePolicy
import org.owasp.html.FilterUrlByProtocolAttributePolicy
import org.owasp.html.HtmlPolicyBuilder
import org.owasp.html.PolicyFactory
import org.owasp.html.Sanitizers

class SanitizeService {

    private static final AttributePolicy INTEGER = new AttributePolicy() {
        public String apply(
            String elementName, String attributeName, String value) {
            int n = value.length();
            if (n == 0) { return null; }
            for (int i = 0; i < n; ++i) {
                char ch = value.charAt(i);
                if (ch == '.') {
                    if (i == 0) { return null; }
                    return value.substring(0, i);  // truncate to integer.
                } else if (!('0' <= ch && ch <= '9')) {
                    return null;
                }
            }
            return value;
        }
    }

    private static final  PolicyFactory POLICY = Sanitizers.FORMATTING
        .and(Sanitizers.LINKS)
        .and(Sanitizers.BLOCKS)
        .and(Sanitizers.STYLES)
        .and(
            new HtmlPolicyBuilder()

                .allowElements("img", "hr", "iframe", "table", "th", "tr", "td", "tbody", "col", "colgroup", "thead", "tfooter", "caption", "code", "pre")
                .allowStandardUrlProtocols()

                .allowAttributes("alt").onElements("img")
                .allowAttributes("border", "height", "width").matching(INTEGER)
                .onElements("img")

                .allowAttributes("width", "height", "frameborder").matching(INTEGER)
                .onElements("iframe")

                .allowAttributes("width", "height", "colspan", "rowspan").matching(INTEGER)
                .onElements("table", "th", "tr", "td", "tbody", "thead", "tfooter", "caption")

                .allowAttributes("class")
                .onElements("img", "hr", "iframe", "table", "th", "tr", "td", "tbody", "col", "colgroup", "thead", "tfooter", "caption", "code", "pre")

                .allowAttributes("target")
                .onElements("a")

                .allowAttributes("src").matching(new FilterUrlByProtocolAttributePolicy(["http", "https"]))
                .onElements("img", "iframe")

                .toFactory()
    )

    def sanitize(def html) {
        POLICY.sanitize(html)
    }
}
