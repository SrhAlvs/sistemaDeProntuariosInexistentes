package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities;

public enum DoctorSpecialty {
    ANESTESIOLOGIA(1, "Anestesiologia"),
    CARDIOLOGIA(2, "Cardiologia"),
    DERMATOLOGIA(3, "Dermatologia"),
    ENDOCRINOLOGIA(4, "Endocrinologia e Metabologia"),
    GASTROENTEROLOGIA(5, "Gastroenterologia"),
    GERIATRIA(6, "Geriatria"),
    GINECOLOGIA_E_OBSTETRICIA(7, "Ginecologia e Obstetrícia"),
    HEMATOLOGIA(8, "Hematologia"),
    INFECTOLOGIA(9, "Infectologia"),
    MASTOLOGIA(10, "Mastologia"),
    MEDICINA_DO_TRABALHO(11, "Medicina do Trabalho"),
    NEFROLOGIA(12, "Nefrologia"),
    NEUROLOGIA(13, "Neurologia"),
    OFTALMOLOGIA(14, "Oftalmologia"),
    ONCOLOGIA(15, "Oncologia"),
    ORTOPEDIA_E_TRAUMATOLOGIA(16, "Ortopedia e Traumatologia"),
    OTORRINOLARINGOLOGIA(17, "Otorrinolaringologia"),
    PEDIATRIA(18, "Pediatria"),
    PNEUMOLOGIA(19, "Pneumologia"),
    PSICOLOGIA(20, "Psicologia"),
    PSIQUIATRIA(21, "Psiquiatria"),
    RADIOLOGIA(22, "Radiologia e Diagnóstico por Imagem"),
    REUMATOLOGIA(23, "Reumatologia"),
    UROLOGIA(24, "Urologia");

    private final int id;
    private final String specialtyName;

    // Construtor do Enum
    DoctorSpecialty(int id, String specialtyName) {
        this.id = id;
        this.specialtyName = specialtyName;
    }

    public int getId() {
        return id;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    // Método utilitário para buscar o enum a partir do ID do banco de dados
    public static DoctorSpecialty deId(int id) {
        for (DoctorSpecialty docSpe : values()) {
            if (docSpe.getId() == id) {
                return docSpe;
            }
        }
        throw new IllegalArgumentException("ID de especialidade inválido: " + id);
    }

}
