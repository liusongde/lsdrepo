grammar Brace;

wholeText
  : partText partText*
  ;
  
partText
  : finalPartText  
  | optionPartText
  ;
  
optionPartText : LEFT_BRACE (optionPartText|finalPartText)+ RIGHT_BRACE  ;   

finalPartText
  : commonText+ 
  ;
  
commonText : parmaText | KEY | COMMONTEXT  //~('[' | ']' | '#')+
;  
  
parmaText : VAR_V KEY VAR_V ;  

LEFT_BRACE : '[';
RIGHT_BRACE : ']';
VAR_V : '#'; 
KEY : [a-z_]+ ;
COMMONTEXT :   ~[a-z_\[\]#]+ ;

