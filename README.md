Amazon ASIN related info scrape 

Author:victorz

Input：ASIN（you can put ASIN info like ASIN1;ASIN2;ASIN3...） Output：Get ASIN、Star、Product rank，and save the info into database

Design detail document：https://docs.qq.com/doc/DZXVOaklyTXVodGlj

Usage： （1）Install mysql on Mac：https://cloud.tencent.com/developer/article/1868895 （2）git clone ...(Git website) （3）mvn clean package -Dmaven.test.skip=true （4）execute DDL sql in src/main/resources/ScrapeInfoDDL.sql （5）java -cp junglescout_zhangdi-1.0-SNAPSHOT-jar-with-dependencies.jar com.js.main.ApplicationMain ASIN1;ASIN2;ASIN3... (Attention!! ";" need to be escape(转义) in some environment)

(if you still have problems,please check Design document,detail instruction is recorded)

Entrance of Main： com.js.main.ApplicationMain

Output: (the amazon filter info has been saved in database) DataBase: You can use Select DML Language to query scrape Amazon Info. Such as: Select * from amazon_info.amazon_info;
