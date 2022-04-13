//package application.testData.generator;
//
//import application.testData.invalidRandomAddresses.easy.done.RandomAddressesWithoutAField;
//import application.testData.model.TestObject;
//
//import java.io.*;
//import java.util.List;
//
//public class AddressesThatNeedToBeCorrectedGenerator {
//    public static void createAddressesThatNeedToBeCorrectedForAGivenFilePath(String filePath) {
//        try {
//            String countryCode = filePath.replace(".txt", "").split("/")[4];
//            new File("./files/test/incorrectRandomAddresses/" + countryCode).mkdirs();
//            String fileName = RandomAddressesWithoutAField.class.getSimpleName() + ".ser";
//            String newFilePath = "./files/test/incorrectRandomAddresses/" + countryCode + "/" + fileName;
//            File file = new File(newFilePath);
//            new FileWriter(file, false).close(); // sterge contentul existent din fisiere
//            file.getParentFile().mkdirs();
//            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true));
//            FileOutputStream fileOut = new FileOutputStream(newFilePath);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            String serializedPath = "./files/test/correctRandomAddresses/" + countryCode + ".ser";
//            List<TestObject> testObjectList = getListObjectWithoutAField(serializedPath);
//            objectOut.writeObject(testObjectList);
//            objectOut.close();
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
