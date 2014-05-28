I.regist({
  'name':'lang.Thread',
  'need':null,
  'fn':function(W,D){
    
    var THREAD = {};
    
    var get = function(name){
      return THREAD[name];
    };
    
    /**
     * 创建
     * @param a a[0]线程名称 a[1]方法体 a[2]休眠间隔
     * @returns 线程
     */
    var create = function(a){
      var o = {
          /** 定时器 */
          'timer':null,
          /** 线程名称 */
          'name':a[0],
          /** 睡眠毫秒数 */
          'sleep':a.length>2?a[2]:12,
          /** 计数器 */
          'count':0,
          /** 状态 0未启动 1运行中 2暂停 */
          'state':0,
          /** 方法体 */
          'run':a[1],
          /**
           * 启动
           */
          'start':function(){
            var k = this;
            if(0 == k.state){
              k.state = 1;
              k.timer = W.setInterval(function(){
                switch(k.state){
                  case 1:
                    k.run(k.count);
                    k.count ++;
                    break;
                }
              },k.sleep);
            }
          },
          /**
           * 停止
           */
          'stop':function(){
            if(1 == this.state || 2 == this.state){
              W.clearInterval(this.timer);
              this.timer = null;
              delete THREAD[this.name];
            }
          },
          /**
           * 暂停
           */
          'pause':function(){
            if(1 == this.state){
              this.state = 2;
            }
          },
          /**
           * 唤醒
           */
          'resume':function(){
            if(2 == this.state){
              this.state = 1;
            }
          }
        };
        THREAD[a[0]] = o;
        return o;
    };
    return {
      'get':function(name){return get(name);},
      'create':function(){return create(arguments);}
    };
  }
});