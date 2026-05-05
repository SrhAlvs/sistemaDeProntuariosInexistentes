package br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util;

import br.edu.ifg.luziania.projetopiii.sistemaDeProntuariosInexistentes.util.exceptions.ValidationException;

import java.util.regex.Pattern;

public class UserValidator {

    // ------------------ USER ------------------

    /*
        NAME: maiúsculas acentuadas ou não ([A-ZÀ-Ú]),
        uma ou mais letras minúsculas acentuadas ou não e apóstrofo ([a-zà-ú']),
        um espaço em branco obrigatório,
        e repete o primeiro padrão, este podendo repetir uma ou mais vezes
    */
    private static final Pattern NAME_PATTERN = Pattern.compile("^([A-ZÀ-Ú][a-zà-ú']+)(\\s[A-ZÀ-Ú][a-zà-ú']+)+$");
    public static void validateName(String name) {
        if (name == null || !NAME_PATTERN.matcher(name).matches()) {
            throw new ValidationException("Falha na validação: Nome não deve conter números ou caracteres especiais, como: '@$#_' .");
        }
    }

    /*
        E-MAIL: ao menos 1 caractere (letras, números, ponto final, hífen) ([\\w.-]),
        aí vem o @,
        seguido do mesmo treco anterior pro domínio ([\\w.-]),
        aí tem o ponto final (.)
        e o 'com' de '.com', você entendeu
    */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    public static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Falha na validação: E-mail '" + email + "' está fora do padrão 'email123@dominio.com'.");
        }
    }

    /*
        PASSWORD: (?=.*[A-Z]) garante que há ao menos uma letra maiúscula,
        ([?=.*\\d]) garante que há ao menos um dígito numérico,
        .{8,} aceita qualquer caractere se no fim tiver 8 dígitos
    */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\\\d).{8,}$");
    public static void validatePassword(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException("Falha na validação: SENHA está fora do padrão (8 caracteres contendo letras, números ou caracteres especiais).");
        }
    }

    // ------------------ DOCTOR ------------------

    /*
        CRM: sequência de 4 a 8 dígitos (\\d{4,8})
        seguido de um hífen (-)
        e duas letras maiúsculas ([A-Z]{2})
    */
    private static final Pattern CRM_PATTERN = Pattern.compile("^\\d{4,8}-[A-Z]{2}$");
    public static void validateCrm(String crm) {
        if (crm == null || !CRM_PATTERN.matcher(crm).matches()) {
            throw new ValidationException("Falha na validação: CRM '" + crm + "' está fora do padrão '123456-UF'.");
        }
    }

    // ------------------ PATIENT ------------------

    /*
        CPF: sequência de 3 dígitos (\\d{3})
        seguidos de um ponto final (.),
        seguidos de uma sequência de 3 dígitos (\\d{3})
        seguidos de um ponto final (.),
        seguidos de uma sequência de 3 dígitos (\\d{3})
        seguidos de um hífen (-) e,
        por fim, seguidos de 2 dígitos (\\d{2})
    */
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
    public static void validateCpf(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            throw new ValidationException("Falha na validação: CPF '" + cpf + "' está fora do padrão '123.456.789-01'.");
        }
    }
}
