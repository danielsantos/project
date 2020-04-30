package com.aplinotech.cadastrocliente.service;

import com.aplinotech.cadastrocliente.model.Usuario;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void confirmarCadastro(Usuario usuario) {
        try {
            String texto = "Olá " + usuario.getNome() + ", " +
                    " \n\n" +
                    " Clique no link abaixo para confirmar seu cadastro no site Estoque Simples: " +
                    " \n\n " +
                    " http://www.estoquesimples.com.br/sysestoque/confirmaCadastro/" + usuario.getToken();

            Email email = getEmail(usuario);
            email.setSubject("Site Estoque Simples - Confirmação de Cadastro");
            email.setMsg(texto);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Email getEmail(Usuario usuario) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.kinghost.net");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("contato@estoquesimples.com.br", "Prj2020Go"));
            email.setSSL(false);
            email.setFrom("contato@estoquesimples.com.br");
            email.addTo(usuario.getEmail());
            return email;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
