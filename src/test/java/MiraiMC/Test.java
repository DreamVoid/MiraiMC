package MiraiMC;


import me.dreamvoid.miraimc.internal.Config;

import java.io.File;
import java.sql.*;

public class Test {
    public static void main(String[] args){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\DreamVoid\\Desktop\\database.db");
            Statement statement = connection.createStatement();

            // 在数据库中创建一个表
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS miraimc_binding (uuid string, qqid long)");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='abcd' LIMIT 1");
            if(!resultSet.isBeforeFirst()){
                statement.executeUpdate("insert into miraimc_binding values('abcd', 1234);");
            } else {
                System.out.println("NotNull");
                statement.executeUpdate("UPDATE miraimc_binding SET qqid=4567 WHERE uuid='abcd';");
            }
            resultSet.close();

            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM miraimc_binding WHERE uuid='abcd' LIMIT 1");
            if(resultSet.isBeforeFirst()) {
                System.out.println(resultSet1.getInt("qqid"));
                System.out.println(resultSet1.getString("uuid"));
                resultSet1.close();
            } else System.out.println("Error");
            // 在表中创建记录
            /*statement.executeUpdate("insert into miraimc_binding values(1, 'tree')");
            statement.executeUpdate("insert into miraimc_binding values(2, 'cindy')");
            statement.executeUpdate("insert into miraimc_binding values(3, 'jack')");*/
            // 在表中获取记录并显示出来
            /*ResultSet resultSet = statement.executeQuery("select * from PERSON");
            while (resultSet.next()) {
                System.out.println("id = " + resultSet.getInt("id"));
                System.out.println("name = " + resultSet.getString("name"));
            }
            resultSet.close();*/
            // 更新表中原有的记录
            /*statement.executeUpdate("update PERSON set name = 'tree2' where id = 3");*/
            // 删除表中的一条记录
            /*statement.executeUpdate("delete from PERSON where id = 3");*/
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
