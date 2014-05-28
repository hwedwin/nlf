/**
 * UUID生成器
 * I.util.UUID.next();
 */
I.regist({
  'name' : 'util.UUID',
  'need' : null,
  'fn' : function(W, D) {
    var next = function() {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
          function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
          });
    };

    return {
      'next' : function() {
        return next();
      }
    };
  }
});