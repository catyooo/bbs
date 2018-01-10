checkSecurity
===
   select * from bbs_user where true 
   and user_name = #user# 
   and security_first_answer = #sfa# 
   and security_second_answer = #ssa# 
   and security_third_answer = #sta#
   
updateUserPass
===
   update bbs_user set password = #np# where user_name = #user#
