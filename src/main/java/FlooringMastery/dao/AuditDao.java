package FlooringMastery.dao;

public interface AuditDao {
    void writeAuditEntry(String entry)
            throws PersistenceException;
}
