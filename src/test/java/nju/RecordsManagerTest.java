package nju;

import nju.bullet.Bullet;
import nju.creature.Creature;
import nju.record.Record;
import nju.record.RecordFactory;
import nju.record.RecordPlayer;
import nju.record.RecordsManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class RecordsManagerTest {

    @Test
    public void testInitialize() {
        RecordsManager recordsManager = new RecordsManager(null);
        assertTrue(recordsManager.isEmpty());
    }

    @Test
    public void testInvalidFileName() {
        RecordsManager recordsManager = new RecordsManager(null);
        recordsManager.loadRecordsFile("dont care", new RecordPlayer(null));
    }

    @Test
    public void testSaveAndLoad() {
        RecordsManager recordsManager = new RecordsManager(null);
        Creature creature = new Creature("1.png");
        Bullet bullet = new Bullet(1, 2, 3, 4, 5, 6, "bullet1.png", creature);

        Record[] records = new Record[7];
        records[0] = RecordFactory.makeCreateRecord(creature.getId(), creature, 0, 0);
        records[1] = RecordFactory.makeMoveRecord(100, creature.getId(), creature.x(), creature.y());
        records[2] = RecordFactory.makeHurtRecord(200, creature.getId(), creature.getHealth());
        records[3] = RecordFactory.makeDeadRecord(300, creature.getId());
        records[4] = RecordFactory.makeBulletCreateRecord(400, bullet.getId(), bullet.x(), bullet.y(), bullet.getAngle(), bullet.getSrc());
        records[5] = RecordFactory.makeBulletMoveRecord(500, bullet.getId(), bullet.x(), bullet.y());
        records[6] = RecordFactory.makeBulletRemoveRecord(600, bullet.getId());
        for (Record record : records)
            recordsManager.addRecord(record);

        File tempFile;
        try {
            tempFile = File.createTempFile("records-", ".xml");
            tempFile.deleteOnExit();
            recordsManager.exportToFile(tempFile.getAbsolutePath());

            RecordPlayer player = new RecordPlayer(null);
            RecordsManager m2 = new RecordsManager(null);
            m2.loadRecordsFile(tempFile.getAbsolutePath(), player);
            assertTrue(m2.getRecords().size() == records.length);
            for (int i = 0; i < m2.getRecords().size(); i++) {
                assertTrue(m2.getRecords().get(i).equals(records[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
