package com.bupt.cmp.compare.base;

import com.bupt.cmp.compare.CompareDirector;
import com.bupt.cmp.compare.CompareResult;
import com.bupt.cmp.compare.Granularity;
import com.bupt.cmp.compare.SimRecord;
import com.bupt.cmp.factory.ICodeFactory;
import com.bupt.cmp.factory.JavaCodeFactory;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JavaInitFile {

    public static ICodeFactory icodeFactory;

    public static CompareDirector compareDirector1 = CompareDirector.getInstance(Granularity.MLCS,0, CompareDirector.TEXT_COMPARE);
    public static CompareDirector compareDirector2 = CompareDirector.getInstance(Granularity.MLCS,0, CompareDirector.TOKEN_COMPARE);
    public static CompareDirector compareDirector3 = CompareDirector.getInstance(Granularity.LINE,0, CompareDirector.SYNTAX_COMPARE);

    static {
        icodeFactory = JavaCodeFactory.getInstance();
    }

    @Test
    public void test1(){//测试
        String folder = "D:\\test\\src\\";
        File file = new File(folder);
        long start = System.currentTimeMillis();
        iteratorFolder(file);
        long end = System.currentTimeMillis();
        System.out.println("total spend "+(end-start)/1000+" seconds");
    }

    private void iteratorFolder(File file) {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                iteratorFolder(f);
            }
        }else if(file.isFile()&&file.getAbsolutePath().endsWith(".java")){
            DefaultCodeFile f = icodeFactory.generateDefectCodeFile(file);
            String textLine = LineStruct.LineStructToString(f.getTextLine());
            String tokenLine = LineStruct.LineStructToString(f.getTokenLine());
            String syntaxLine = StoreNode.StoreNodeToString(f.getTree());
            File newFile = new File(file.getAbsolutePath()+".pretreat");
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(newFile);
//                fileWriter.write(textLine);
//                fileWriter.write("\n");
//                fileWriter.write(tokenLine);
//                fileWriter.write("\n");
                fileWriter.write(syntaxLine);
            }catch (Exception e){
                log.error("error in file writer {}",e);
            }finally {
                if(fileWriter!=null){
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        log.error("????");
                    }
                }
            }
        }else {
            log.error("????");
        }
    }


    @Test
    public void test2(){
        String folder = "D:\\test\\源代码缺陷检测\\Demo1.java";
        File file = new File(folder);
        long start = System.currentTimeMillis();
        String textLine = ",1:-1788429396,3:638638051,4:-1090180163,5:-1061551012,6:1922262588,7:404,12:-660794657,13:516678635,14:404,16:295499157,17:-2003734981,18:404,19:125";
        String tokenLine = ",1:1403617087,3:-1213864461,4:1093918484,6:1183997013,7:746396961,12:-397501611,13:-1247068735,14:746396961,16:-244081895,17:-1157910604,18:746396961";
        String syntLine = ",1:19:6410292883:0:126:126,1:19:6363166329:47126554:125:1,1:19:6218332602:144833727:123:2,1:19:6217843779:488823:122:1,3:7:4344613557:61986105:89:1,3:7:4165957075:178656482:87:2,3:7:3868739081:297217994:83:4,6:6:1316693782:2552045299:30:53,6:6:1262596469:54097313:29:1,6:6:1260882421:1714048:28:1,6:6:1217786706:43095715:27:1,4:4:1235517977:81009345:26:1,5:5:1235517977:1316527322:26:27,4:4:1181420664:54097313:25:1,5:5:1181420664:54097313:25:1,6:6:1069509607:148277099:23:4,16:18:1138374272:5079469507:22:100,4:4:1033451067:147969597:22:3,5:5:1033451067:147969597:22:3,6:6:990530859:78978748:22:1,6:6:979984439:10546420:21:1,16:18:959717790:178656482:20:2,4:4:959568469:73882598:20:2,5:5:959568469:73882598:20:2,6:6:930316959:49667480:20:1,4:4:933984813:25583656:19:1,5:5:933984813:25583656:19:1,6:6:867096153:63220806:19:1,4:4:870764007:63220806:18:1,5:5:870764007:63220806:18:1,6:6:783667829:83428324:18:1,4:4:787335683:83428324:17:1,5:5:787335683:83428324:17:1,6:6:769680496:13987333:17:1,4:4:773348350:13987333:16:1,5:5:773348350:13987333:16:1,6:6:735675578:34004918:16:1,4:4:739343432:34004918:15:1,5:5:739343432:34004918:15:1,6:6:674053122:61622456:15:1,4:4:677720976:61622456:14:1,5:5:677720976:61622456:14:1,6:6:645499688:28553434:14:1,4:4:649167542:28553434:13:1,5:5:649167542:28553434:13:1,6:6:603592664:41907024:13:1,4:4:607260518:41907024:12:1,5:5:607260518:41907024:12:1,6:6:565298297:38294367:12:1,4:4:568966151:38294367:11:1,5:5:568966151:38294367:11:1,6:6:498697732:66600565:11:1,16:18:468754767:490963023:11:9,12:14:672869845:4406599662:10:90,4:4:502365586:66600565:10:1,5:5:502365586:66600565:10:1,6:6:443418992:55278740:10:1,17:17:387745422:81009345:10:1,4:4:447086846:55278740:9:1,5:5:447086846:55278740:9:1,6:6:362240831:81178161:9:1,17:17:333648109:54097313:9:1,12:14:494213363:178656482:8:2,4:4:365908685:81178161:8:1,5:5:365908685:81178161:8:1,6:6:349805231:12435600:8:1,17:17:331934061:1714048:8:1,4:4:353473085:12435600:7:1,5:5:353473085:12435600:7:1,6:6:336232107:13573124:7:1,16:16:321528394:169434629:7:2,17:17:288838346:43095715:7:1,4:4:339899961:13573124:6:1,5:5:339899961:13573124:6:1,6:6:268303683:67928424:6:1,16:16:244781108:76747286:6:1,4:4:271971537:67928424:5:1,5:5:271971537:67928424:5:1,16:16:229898184:14882924:5:1,6:6:182719389:85584294:5:1,12:14:196995369:297217994:4:4,4:4:186387243:85584294:4:1,5:5:186387243:85584294:4:1,6:6:148277099:34442290:4:1,4:4:151944953:34442290:3:1,5:5:151944953:34442290:3:1,17:17:140561247:148277099:3:4,16:16:139444463:22152588:3:1,13:13:115986024:81009345:3:1,6:6:108814699:39462400:3:1,6:6:108814699:39462400:3:1,17:17:108814699:39462400:3:1,3:3:127783365:169434629:2:2,12:12:127783365:169434629:2:2,4:4:112482553:39462400:2:1,5:5:112482553:39462400:2:1,4:4:101904961:46064636:2:1,5:5:101904961:46064636:2:1,6:6:63676140:45138559:2:1,6:6:63676140:45138559:2:1,17:17:63676140:45138559:2:1,13:13:61888711:54097313:2:1,17:17:61582499:78978748:2:1,16:16:41439354:98005109:2:1,3:3:92021522:77413107:1:1,12:12:92021522:77413107:1:1,16:16:92021522:77413107:1:1,1:1:89597671:55236056:1:1,3:3:89597671:89058811:1:1,12:12:89597671:89058811:1:1,16:16:89597671:89058811:1:1,4:4:68301133:5581465:1:1,5:5:68301133:5581465:1:1,16:16:68301133:161597051:1:4,4:4:67343994:45138559:1:1,5:5:67343994:45138559:1:1,13:13:60174663:1714048:1:1,3:3:51036079:76747286:1:1,12:12:51036079:76747286:1:1,17:17:51036079:10546420:1:1,16:16:25539352:15900002:1:1,6:6:18537581:45138559:1:1,6:6:18537581:45138559:1:1,17:17:18537581:45138559:1:1,4:4:3899852:98005109:1:1,5:5:3899852:98005109:1:1";
        DefaultCodeFile f = icodeFactory.generateDefectCodeFile(file);
        System.out.println(LineStruct.LineStructToString(f.getTextLine()).equals(textLine));
        System.out.println(LineStruct.LineStructToString(f.getTokenLine()).equals(tokenLine));
        System.out.println(StoreNode.StoreNodeToString(f.getTree()).equals(syntLine));
        List<LineStruct> textList = LineStruct.StringToLineStruct(textLine);
        List<LineStruct> tokenList = LineStruct.StringToLineStruct(tokenLine);
        List<StoreNode> syntList = StoreNode.StringToStoreNode(syntLine);
        CompareResult compareResult1 = compareDirector1.compare(f.getTextLine(),textList);
        CompareResult compareResult2 = compareDirector2.compare(f.getTokenLine(),tokenList);
        CompareResult compareResult3 = compareDirector3.compareSyntax(syntList,f.getTree());
        long end = System.currentTimeMillis();
        System.out.println(" spend "+(end-start)/1000.0+" seconds!");
        System.out.println(compareResult1.getSimvalue());
        System.out.println(compareResult2.getSimvalue());
        System.out.println(compareResult3.getSimvalue());
        System.out.println(SimRecord.SimRecordtoString(compareResult1.getSimRecords()));
        System.out.println(SimRecord.SimRecordtoString(compareResult2.getSimRecords()));
        System.out.println(SimRecord.SimRecordtoString(compareResult3.getSimRecords()));
    }
}
