I.regist('util.Template',function(W,D){
var CFG={
 prefix:['{','$'],
 suffix:['$','}'],
 eq:'=',
 key:'data',
 print_prefix:'printf(',
 print_suffix:');'
},POOL={};
function StringReader(os){
 var r=os,readed=0;
 var total=r.length;
 var hasNext=function(){
  return readed<total;
 };
 var next=function(){
  var s=r.substring(readed,readed+1);
  readed++;
  switch(s){
   case '\r':
   case '\n':s=' ';break;
  }
  return s;
 };
 var testNext=function(n){
  var q=[],i=0;
  while(hasNext()&&i<n){
   q.push(next());
   i++;
  }
  readed-=i;
  return q.join('');
 };
 var skip=function(n){
  for(var i=0;i<n;i++){
   if(hasNext()) next();
   else break;
  }
 };
 var readUntil=function(ss){
  var q=[],n=ss.length,s,i;
  while(hasNext()){
   s=testNext(1);
   for(i=0;i<n;i++){
    if(s==ss[i]) return q.join('');
   }
   q.push(s);
   skip(1);
  }
  return q.join('');
 };
 return {
  hasNext:function(){return hasNext();},
  next:function(){return next();},
  testNext:function(n){return testNext(n);},
  skip:function(n){skip(n);},
  readUntil:function(){return readUntil(arguments);}
 };
}
var _compile=function(os){
 var prefix=CFG.prefix.join(''),suffix=CFG.suffix.join('');
 if(undefined != POOL[os]) return POOL[os];
 var r=new StringReader(os),q=[];
 var getQ=function(){
  q.unshift('var _s=[];');
  q.push('return _s.join(\'\');');
  return new Function(CFG.key,q.join('\r\n'));
 };
 var addQ=function(s){
  s=s.replace(/\'/g,'\\\'');
  q.push('_s.push(\''+s+'\');');
 };
 var addJs=function(s){
  q.push(s);
 };
 var addVar=function(v){
  q.push('_s.push('+v+');');
 };
 var loop=function(){
  var p=r.readUntil(CFG.prefix[0]);
  addQ(p);
  if(!r.hasNext()) return;
  if(prefix==r.testNext(2)){
   r.skip(2);
   var isV=false;
   if(CFG.eq==r.testNext(1)){
    isV=true;
    r.skip(1);
   }
   var tp=[];
   while(suffix!=r.testNext(2)&&r.hasNext()){
    p=r.readUntil(CFG.suffix[0]);
    tp.push(p);
    if(suffix==r.testNext(2)){
     r.skip(2);
     break;
    }else tp.push(r.next());
   }
   if(isV) addVar(tp.join(''));
   else{
    var js=tp.join(''),buf=[];
    var idx=js.indexOf(CFG.print_prefix);
    while(idx>-1){
     buf.push(js.substr(0,idx));
     js=js.substr(idx+CFG.print_prefix.length);
     idx=js.indexOf(CFG.print_suffix);
     buf.push('_s.push(');
     buf.push(js.substr(0,idx));
     buf.push(');');
     js=js.substr(idx+CFG.print_suffix.length);
     idx=js.indexOf(CFG.print_prefix);
    }
    buf.push(js);
    addJs(buf.join(''));
   }
   if(r.hasNext()) loop();
  }else{
   addQ(r.next());
   loop();
  }
 };
 loop();
 var rs=getQ();
 POOL[os]=rs;
 return rs;
};
var _render=function(data,tpl){
 var func=_compile(tpl);
 return func.call(func,data);
};
return {
 compile:function(tplStr){return _compile(tplStr);},
 render:function(data,tplStr){return _render(data,tplStr);}
};
}+'');