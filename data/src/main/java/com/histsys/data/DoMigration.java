//package com.histsys.data;
//
//import com.histsys.data.config.Env;
//import io.github.biezhi.anima.Anima;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//import static io.github.biezhi.anima.Anima.execute;
//import static io.github.biezhi.anima.Anima.select;
//
///**
// * 数据库更新（表、字段等等）版本管理
// */
//public class DoMigration {
//
//    private static Logger logger = Logger.getLogger(DoMigration.class.getName());
//
//    private static final String DB_TABLE_NAME = "db_migrations";
//
//    public static void main(String[] args) throws IOException {
//        initDatabase();
//        doMigration();
//    }
//
//    private static void initDatabase() {
//        String dbUrl = Env.get("HISTSYS_DB_URL", "jdbc:postgresql://localhost:5433/histsys_dev");
//        String dbUser = Env.get("HISTSYS_DB_USERNAME", "neo");
//        String dbPass = Env.get("HISTSYS_DB_PASSWORD", "");
//        Anima.open(dbUrl, dbUser, dbPass);
//    }
//
//    private static void doMigration() throws IOException {
//        // db_migrations 表建立
//        execute("CREATE TABLE IF NOT EXISTS " + DB_TABLE_NAME + " (version varchar(20))");
//        // 历史版本检查
//        List<String> currentVersions = select().bySQL(String.class, "SELECT version FROM db_migrations").all();
//        // 版本合并
//        checkVersionFiles(currentVersions);
//    }
//
//    private static void checkVersionFiles(List<String> currentVersions) throws IOException {
//        URL migrationUrl = DoMigration.class.getClassLoader().getResource("migrations");
//        assert migrationUrl != null;
//        File dir = new File(migrationUrl.getPath());
//        logger.info("===========================Migration==========================");
//        if (dir.isDirectory()) {
//            List<File> files = Arrays.stream(Objects.requireNonNull(dir.listFiles())).sorted().collect(Collectors.toList());
//            for (File file : files) {
//                if (file.getName().endsWith(".sql")) { // length(202106190001.sql) = 16
//                    String version = file.getName().replace(".sql", "");
//                    if (!currentVersions.contains(version)) { // 当前版本未被执行
//                        // execute
//                        String sql = Files.readString(file.toPath());
//                        logger.info("Migration file execute: " + file.getName());
//                        execute(sql);
//                        // insert new record into migration table
//                        execute("INSERT INTO " + DB_TABLE_NAME + "(version) VALUES(" + version + ")");
//                    } else {
//                        logger.info("Migration file ignore: " + file.getName());
//                    }
//                }
//            }
//        }
//        logger.info("===========================Migration==========================");
//    }
//}
