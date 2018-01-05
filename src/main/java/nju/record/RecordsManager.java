package nju.record;

import java.util.*;
import java.io.*;

import nju.Field;
import nju.Thing2D;
import nju.creature.Creature;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class RecordsManager {
    private ArrayList<Record> records = new ArrayList<Record>();
    private Field field;

    public RecordsManager(Field field) {
        this.field = field;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public boolean isEmpty() { return records.isEmpty(); }

    public synchronized void addRecord(Record record) {
        records.add(record);
        if (record.type == Record.RecordType.CREATE) {
            System.out.printf("%s created at (%d, %d)\n", record.creature.getClass().getSimpleName(), record.positionX, record.positionY);
            this.field.addToBoard(String.format("%s created at (%d, %d)\n", record.creature.getClass().getSimpleName(), record.positionX, record.positionY));
        }
//        else if (record.type == Record.RecordType.MOVE) {
//            System.out.printf("%d move to (%d, %d)\n", record.id, this.field.convertXtoPosition(record.x), this.field.convertYtoPosition(record.y));
//        }
        else if (record.type == Record.RecordType.HURT) {
            System.out.printf("creature #%d hurt, health is %f\n", record.id, record.health);
//            this.field.addToBoard(String.format("creature #%d hurt, health is %f\n", record.id, record.health));
        } else if (record.type == Record.RecordType.DEAD) {
            System.out.printf("creature #%d dead\n", record.id);
            this.field.addToBoard(String.format("creature #%d dead\n", record.id));
        }
    }

    public void loadRecordsFile(String fileName, RecordPlayer player) {
        records.clear();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);

            NodeList recordsXML = document.getElementsByTagName("record");

            System.out.printf("Total records count: %d\n", recordsXML.getLength());

            for (int i = 0; i < recordsXML.getLength(); i++) {
                Node record = recordsXML.item(i);
                String type = record.getAttributes().getNamedItem("type").getNodeValue();
                switch (Record.RecordType.valueOf(type)) {
                    case CREATE: {
                        String creatureName = record.getAttributes().getNamedItem("creature").getNodeValue();
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        String src = record.getAttributes().getNamedItem("src").getNodeValue();
                        int positionX = Integer.parseInt(record.getAttributes().getNamedItem("positionX").getNodeValue());
                        int positionY = Integer.parseInt(record.getAttributes().getNamedItem("positionY").getNodeValue());
                        Creature creature = new Creature(src);
                        creature.setX(this.field.convertPositionToX(positionX));
                        creature.setY(this.field.convertPositionToY(positionY));
                        creature.setId(id);
                        player.addCreature(creature, id);
                        addRecord(RecordFactory.makeCreateRecord(creature.getId(), creature, positionX, positionY));
                        break;
                    }
                    case MOVE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        int x = Integer.parseInt(record.getAttributes().getNamedItem("x").getNodeValue());
                        int y = Integer.parseInt(record.getAttributes().getNamedItem("y").getNodeValue());
                        addRecord(RecordFactory.makeMoveRecord(time, id, x, y));
                        break;
                    }
                    case HURT: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        double health = Double.parseDouble(record.getAttributes().getNamedItem("health").getNodeValue());
                        addRecord(RecordFactory.makeHurtRecord(time, id, health));
                        break;
                    }
                    case DEAD: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        addRecord(RecordFactory.makeDeadRecord(time, id));
                        break;
                    }
                    case BULLET_CREATE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        int x = Integer.parseInt(record.getAttributes().getNamedItem("x").getNodeValue());
                        int y = Integer.parseInt(record.getAttributes().getNamedItem("y").getNodeValue());
                        double angle = Double.parseDouble(record.getAttributes().getNamedItem("angle").getNodeValue());
                        String src = record.getAttributes().getNamedItem("src").getNodeValue();
                        addRecord(RecordFactory.makeBulletCreateRecord(time, id, x, y, angle, src));
                        break;
                    }
                    case BULLET_MOVE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        int x = Integer.parseInt(record.getAttributes().getNamedItem("x").getNodeValue());
                        int y = Integer.parseInt(record.getAttributes().getNamedItem("y").getNodeValue());
                        addRecord(RecordFactory.makeBulletMoveRecord(time, id, x, y));
                        break;
                    }
                    case BULLET_REMOVE: {
                        int id = Integer.parseInt(record.getAttributes().getNamedItem("id").getNodeValue());
                        long time = Long.parseLong(record.getAttributes().getNamedItem("time").getNodeValue());
                        addRecord(RecordFactory.makeBulletRemoveRecord(time, id));
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("Load records from %s successfully\n", fileName);
    }

    public void exportToFile(String fileName) {
        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
            return;
        }

        Element root = document.createElement("records");
        document.appendChild(root);
        for (Record record : records) {
            Element recordNode = document.createElement("record");
            if (record.type == null) {
                System.err.println(record);
            }
            recordNode.setAttribute("type", record.type.toString());
            switch (record.type) {
                case CREATE: {
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    recordNode.setAttribute("creature", record.creature.getClass().getName());
                    recordNode.setAttribute("positionX", String.valueOf(record.positionX));
                    recordNode.setAttribute("positionY", String.valueOf(record.positionY));
                    recordNode.setAttribute("src", record.src);
                    break;
                }
                case MOVE: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    recordNode.setAttribute("x", String.valueOf(record.x));
                    recordNode.setAttribute("y", String.valueOf(record.y));
                    break;
                }
                case HURT: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    recordNode.setAttribute("health", String.valueOf(record.health));
                    break;
                }
                case DEAD: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    break;
                }
                case BULLET_CREATE: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    recordNode.setAttribute("x", String.valueOf(record.x));
                    recordNode.setAttribute("y", String.valueOf(record.y));
                    recordNode.setAttribute("angle", String.valueOf(record.angle));
                    recordNode.setAttribute("src", record.src);
                    break;
                }
                case BULLET_MOVE: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    recordNode.setAttribute("x", String.valueOf(record.x));
                    recordNode.setAttribute("y", String.valueOf(record.y));
                    break;
                }
                case BULLET_REMOVE: {
                    recordNode.setAttribute("time", String.valueOf(record.time));
                    recordNode.setAttribute("id", String.valueOf(record.id));
                    break;
                }
            }
            root.appendChild(recordNode);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.printf("Records write to %s successfully\n", fileName);
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
}
