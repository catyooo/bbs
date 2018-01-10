
getNewsVerLog
===
    select * from bbs_ver_log order by create_time desc limit 1
    
getTheLastOneVL
===
    select * from bbs_ver_log where id = (select id from bbs_ver_log where id < 
    (select id from bbs_ver_log order by create_time desc limit 1) order by id desc limit 1);