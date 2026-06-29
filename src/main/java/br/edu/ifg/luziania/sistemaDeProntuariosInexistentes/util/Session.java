package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.User;

public class Session {
    private static User currentUser;
    private static Doctor currentDoctor;
    private static Patient currentPatient;

    public static void loginDoctor(Doctor doctor) {
        currentUser = new User(doctor.getIdUser(), doctor.getName(), doctor.getEmail(), "DOCTOR");
        currentDoctor = doctor;
        currentPatient = null;

        LogWriter.write("[USUÁRIO ATIVO] Doutor: " + doctor.getName());
    }

    public static void loginPatient(Patient patient) {
        currentUser = new User(patient.getIdUser(), patient.getName(), patient.getEmail(), "PATIENT");
        currentPatient = patient;
        currentDoctor = null;

        LogWriter.write("[USUÁRIO ATIVO] Paciente: " + patient.getName());
    }

    public static void logout() {
        currentUser = null;
        currentDoctor = null;
        currentPatient = null;

        LogWriter.write("[LOG OUT] Sem usuários ativos.");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public static Patient getCurrentPatient() {
        return currentPatient;
    }
}
