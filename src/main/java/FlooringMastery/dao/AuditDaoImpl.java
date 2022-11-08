package FlooringMastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AuditDaoImpl implements AuditDao {
    public static final String AUDIT_FILE = "AuditLog/audit.txt";

    @Override
    public void writeAuditEntry(String entry)
            throws PersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new PersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime timeStamp = LocalDateTime.now();
        out.println(timeStamp + " : " + entry);
        out.flush();
    }
}
