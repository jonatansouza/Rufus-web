/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.factory;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 *
 * @author jonatan
 */
public class HTMLSanitizer {

    private static final PolicyFactory policy = new HtmlPolicyBuilder()
            .allowElements("strong", "i")
            .disallowElements("script")
            .toFactory();

    public static String saniteze(String text) {
        return policy.sanitize(text);
    }
}
