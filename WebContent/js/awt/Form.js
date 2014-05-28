I.regist({
  'name' : 'awt.Form',
  'need' : [ 'net.Rmi', 'ui.Tip', 'ui.ToolTip', 'awt.Label', 'awt.Text',
      'awt.Password', 'awt.Hidden', 'awt.TextArea', 'awt.Upload',
      'awt.ListView', 'awt.ImageView', 'awt.CheckBox', 'awt.NormalSelect',
      'awt.Select', 'awt.List', 'awt.SearchList', 'awt.Button', 'awt.Calendar',
      'awt.Line', 'awt.FormClear','z.UEditor', 'awt.TextRich','awt.VCode' ],
  'fn' : function(W, D) {
    var TYPES = {
      'hidden' : 'awt.Hidden',
      'password' : 'awt.Password',
      'text' : 'awt.Text',
      'textarea' : 'awt.TextArea',
      'rich' : 'awt.TextRich',
      'button' : 'awt.Button',
      'label' : 'awt.Label',
      'select' : 'awt.Select',
      'normalSelect' : 'awt.NormalSelect',
      'list' : 'awt.List',
      'search' : 'awt.SearchList',
      'checkbox' : 'awt.CheckBox',
      'date' : 'awt.Calendar',
      'upload' : 'awt.Upload',
      'editor' : 'z.UEditor',
      'listView' : 'awt.ListView',
      'image' : 'awt.ImageView',
      'line' : 'awt.Line',
      'clear':'awt.FormClear',
      'vcode':'awt.VCode'
    };
    var CAN_POST = {
      'awt.Hidden' : true,
      'awt.Text' : true,
      'awt.Password' : true,
      'awt.TextArea' : true,
      'awt.TextRich' : true,
      'awt.Select' : true,
      'awt.NormalSelect' : true,
      'awt.List' : true,
      'awt.SearchList' : true,
      'awt.CheckBox' : true,
      'awt.Calendar' : true,
      'awt.Upload' : true,
      'awt.ListView' : true,
      'awt.ImageView' : true,
      'z.UEditor' : true,
      'awt.VCode':true,
      'awt.Line' : false,
      'awt.FormClear':false
    };
    var DEFAULT_TYPE = 'text';
    var create = function(p, st) {
      p = I.$(p);
      var obj = I.awt.Component.create();
      var o = I.insert('div', p);
      var instance = {
        'layer' : o,
        'pool' : {},
        'binds' : {},
        'btnLayer' : null
      };
      obj['instance'] = instance;
      obj['type'] = 'awt.Form';
      obj['getInstance'] = function() {
        return this.instance;
      };
      obj['close'] = function() {
        var inst = this.instance;
        inst.layer.parentNode.removeChild(inst);
        delete inst;
      };
      obj['add'] = function(s) {
        var inst = this.instance;
        I.get(TYPES[s.type ? s.type : DEFAULT_TYPE], function(m) {
          var qq = inst.layer;
          if ('button' == s.type) {
            if (!inst.btnLayer) {
              var tt = I.insert('div', inst.layer);
              inst.btnLayer = tt;
            }
            qq = inst.btnLayer;
          }
          var mm = m.create(qq, s);
          inst.pool[s.id] = mm;
        });
      };
      obj['bind'] = function(s) {
        for ( var i in s) {
          instance.binds[i] = s[i];
        }
      };
      obj['get'] = function(id) {
        this.add({'type':'clear'});
        return this.instance.pool[id];
      };
      obj['from'] = function(c, m, args, f) {
        var inst = this.instance;
        if (args) {
          for ( var i in args) {
            I.net.Rmi.set(i, args[i]);
          }
        }
        I.net.Rmi.call(c, m, function(r) {
          for ( var i in inst.pool) {
            var el = inst.pool[i];
            //I.lang.System.out.println(i+' = '+el.render+' = '+r[el.render]);
            // if(el.type&&CAN_POST[el.type]){
            if (undefined != r[el.render]) {
              if (el.rule) {
                el.setValue(el.rule(r[el.render]));
              } else {
                el.setValue(r[el.render]);
              }
            }else{
              if(el.rule){
                el.setValue(el.rule(null));
              }
            }
            // }
          }
          if (f) {
            f(r);
          }
        });
      };
      obj['post'] = function(c, m, f, ef) {
        var inst = this.instance;
        for ( var i in inst.pool) {
          var el = inst.pool[i];
          el.normal();
          if (el.type && CAN_POST[el.type]) {
            I.net.Rmi.set(i, el.value());
          }
        }
        for ( var i in inst.binds) {
          var el = inst.binds[i];
          if (undefined != el) {
            var em = I.$(el);
            if (null == em) {
              I.net.Rmi.set(i, el);
            } else {
              var r = em;
              if (em.tagName) {
                switch (em.tagName.toLowerCase()) {
                case 'img':
                  r = em.src;
                  break;
                case 'input':
                  r = em.value;
                  break;
                case 'select':
                  r = em.value;
                  break;
                case 'textarea':
                  r = em.value;
                  break;
                default:
                  r = em.innerHTML;
                  break;
                }
              }
              I.net.Rmi.set(i, r);
            }
          }
        }
        I.net.Rmi.call(c, m, function(r) {
          f(r);
        }, function(e) {
          if (ef) {
            ef(e);
          } else {
            if (!e.xtype) {
              if (e.msg) {
                I.ui.Tip.create(e);
              }
            }
          }
          if (e.data) {
            try {
              I.each(e.data, function(q, j) {
                inst.pool[q].bad();
                I.ui.ToolTip.create(inst.pool[q].instance.layer, e.msg);
              });
            } catch (ex) {
            }
          }
        });
      };
      obj['normal'] = function(q) {
        var inst = this.instance;
        I.each(q, function(n, i) {
          try {
            inst.pool[n].normal();
          } catch (e) {
          }
        });
      };
      obj['bad'] = function(q) {
        var inst = this.instance;
        I.each(q, function(n, i) {
          try {
            inst.pool[n].bad();
          } catch (e) {
          }
        });
      };
      if (st) {
        I.each(st, function(n, i) {
          obj.add(n);
        });
      }
      return obj;
    };

    return {
      'create' : function(p, st) {
        return create(p, st);
      }
    };
  }
});