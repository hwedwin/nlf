I.regist({
  'name':'util.HashMap',
  'need':['util.Set','util.MapEntry'],
  'fn':function(W,D){
    var create = function(){
      /** 映射 */
      var kv = {};
      
      /**
       * 清空
       */
      var clear = function(){
        for(var i in kv){
          delete kv[i];
        }
      };
      
      /**
       * 是否包含键
       * @param s 键
       * @return true/false 包含/不包含
       */
      var containsKey = function(s){
        for(var i in kv){
          if(i == s){
            return true;
          }
        }
        return false;
      };
      
      /**
       * 是否包含值
       * @param s 值
       * @return true/false 包含/不包含
       */
      var containsValue = function(s){
        for(var i in kv){
          if(kv[i] == s){
            return true;
          }
        }
        return false;
      };
      
      /**
       * 返回此映射所包含的映射关系的Set
       * @return 映射关系的Set
       */
      var entrySet = function(){
        var set = I.util.Set.create();
        for(var i in kv){
          set.add(I.util.MapEntry.create(i,kv[i]));
        }
        return set;
      };
      
      /**
       * 获取值，如果不存在，返回null
       * @param s 键
       * @return 值
       */
      var get = function(s){
        var v = kv[s];
        return v?v:null;
      };
      
      /**
       * 如果映射不包含键-值映射关系，则返回 true
       * @return true/false
       */
      var isEmpty = function(){
        for(var i in kv){
          if(i){}
          return true;
        }
        return false;
      };
      
      /**
       * 返回此映射中所包含的键的Set视图
       * @return 键的Set
       */
      var keySet = function(){
        var set = I.util.Set.create();
        for(var i in kv){
          set.add(i);
        }
        return set;
      };
      
      /**
       * 在映射中关联指定值与指定键
       * @param k 键
       * @param v 值
       */
      var put = function(k,v){
        kv[k] = v;
        return v;
      };
      
      /**
       * 将指定映射的所有映射关系复制到映射中
       * @param okv 原映射
       */
      var putAll = function(okv){
        for(var i in okv){
          kv[i] = okv[i];
        }
      };
      
      /**
       * 删除映射关系
       * @param k 键
       */
      var remove = function(k){
        var o = kv[k];
        delete kv[k];
        return o;
      };
      
      /**
       * 获取映射数量
       * @return 映射数量
       */
      var size = function(){
        var l = 0;
        for(var i in kv){
          if(i){}
          l++;
        }
        return l;
      };
      
      /**
       * 返回此映射所包含的值的Collection视图
       * @return 值的Collection
       */
      var values = function(){
        var cl = I.util.Collection.create();
        for(var i in kv){
          cl.add(kv[i]);
        }
        return cl;
      };
      
      var obj = {
        'clear':function(){clear();},
        'containsKey':function(k){return containsKey(k);},
        'containsValue':function(k){return containsValue(k);},
        'entrySet':function(){return entrySet();},
        'get':function(k){return get(k);},
        'isEmpty':function(){return isEmpty();},
        'keySet':function(){return keySet();},
        'put':function(k,v){return put(k,v);},
        'putAll':function(kv){putAll(kv);},
        'remove':function(k){return remove(k);},
        'size':function(){return size();},
        'values':function(){return values();}
      };
      return obj;
    };
    
    return {
      'create':function(){return create();}
    };
  }
});