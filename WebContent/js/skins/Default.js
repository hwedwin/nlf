I.regist({
  'name':'skins.Default',
  'need':null,
  'fn':function(W,D){
  
    /** 皮肤目录 */
    var DIR = 'skins/Default/';
    
    /**
     * 获取资源
     * @param s 资源文件名
     * @return 资源完整路径
     */
    var getRes = function(s){
      return I.dir()+DIR+s;
    };
    
    var CFG = {
      'seperator':'：',
      'ui':{
        'Tip':{
          css:
             '.i-ui-Tip{position:absolute;margin:0;padding:0;left:0;top:0;}'
             +'.i-ui-Tip b{display:block;margin:0;padding:0;font-size:0;margin-left:5px;margin-right:5px;height:1px;background-color:#000}'
             +'.i-ui-Tip i{display:block;margin:0;padding:0;font-size:0;margin-left:3px;margin-right:3px;height:1px;border:0;border-left:2px solid #000;border-right:2px solid #000;background-color:#000}'
             +'.i-ui-Tip u{display:block;margin:0;padding:0;font-size:0;margin-left:2px;margin-right:2px;height:1px;border:0;border-left:1px solid #000;border-right:1px solid #000;background-color:#000}'
             +'.i-ui-Tip q{display:block;margin:0;padding:0;font-size:0;margin-left:1px;margin-right:1px;height:2px;border:0;border-left:1px solid #000;border-right:1px solid #000;background-color:#000}'
             +'.i-ui-Tip p{display:block;margin:0;padding:0;font-size:1em;text-align:center;height:2em;line-height:2em;overflow:hidden;border:0;border-left:1px solid #000;border-right:1px solid #000;background-color:#000;color:#FFF;padding-left:1em;padding-right:1em}'
        },
        'ToolTip':{
          'bgcolor':'#FEF9D9',
          'fgcolor':'#C40000',
          'border':'0',
          'arrow':getRes('ui.ToolTip.Arrow.gif'),
          'hbar':getRes('ui.ToolTip.HBar.gif'),
          'vbar':getRes('ui.ToolTip.VBar.gif'),
          'corner':getRes('ui.ToolTip.Corner.gif')
        },
        'Loading':{
          'color':'#29D'
        }
      },
      'awt':{
        'margin':'20',
        'Text':{
          'width':'160',
          'height':'24',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.Text.gif')+'\')'
          },
          'focus':{
            'border':'1px solid #3298FE',
            'backgroundImage':'url(\''+getRes('awt.Text.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.Text.gif')+'\')'
          }
        },
        'List':{
          'width':'160',
          'height':'24',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.List.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.List.gif')+'\')'
          }
        },
        'ListView':{
          'width':'260',
          'height':'96',
          'item':{
            'height':'24'
          },
          'normal':{
            'border':'1px solid #EEE'
          },
          'bad':{
            'border':'1px solid #C40000'
          }
        },
        'ImageView':{
          'width':'160',
          'height':'160',
          'normal':{
            'border':'1px solid #EEE',
            'backgroundColor':'#F3F3F3'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundColor':'#F3F3F3'
          }
        },
        'SearchList':{
          'width':'160',
          'height':'24',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.SearchList.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.SearchList.gif')+'\')'
          }
        },
        'Select':{
          'width':'160',
          'height':'24',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.Select.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.Select.gif')+'\')'
          }
        },
        'Upload':{
          'width':'160',
          'height':'24',
          'barImage':'url(\''+getRes('awt.Upload.Bar.gif')+'\')',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.Upload.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.Upload.gif')+'\')'
          }
        },
        'Calendar':{
          'width':'160',
          'height':'24',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.Calendar.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.Calendar.gif')+'\')'
          }
        },
        'TextArea':{
          'width':'260',
          'height':'80',
          'normal':{
            'border':'1px solid #999999',
            'backgroundImage':'url(\''+getRes('awt.TextArea.gif')+'\')'
          },
          'focus':{
            'border':'1px solid #3298FE',
            'backgroundImage':'url(\''+getRes('awt.TextArea.gif')+'\')'
          },
          'bad':{
            'border':'1px solid #C40000',
            'backgroundImage':'url(\''+getRes('awt.TextArea.gif')+'\')'
          }
        },
        'Button':{
          'left':{
            'width':'6'
          },
          'center':{
            'padding':'10'
          },
          'right':{
            'width':'6'
          },
          'height':'29',
          'backgroundImage':'url(\''+getRes('awt.Button.gif')+'\')',
          'normal':{
            'left':{
              'backgroundPosition':'0 0'
            },
            'center':{
              'backgroundPosition':'0 -58px'
            },
            'right':{
              'backgroundPosition':'-6px 0'
            }
          },
          'hover':{
            'left':{
              'backgroundPosition':'0 -29px'
            },
            'center':{
              'backgroundPosition':'0 -87px'
            },
            'right':{
              'backgroundPosition':'-6px -29px'
            }
          }
        },
        'CheckBox':{
          'width':'15',
          'height':'15',
          'checkOn':{
            'backgroundImage':'url(\''+getRes('awt.CheckBox.gif')+'\')',
            'backgroundPosition':'0 -15px'
          },
          'checkOff':{
            'backgroundImage':'url(\''+getRes('awt.CheckBox.gif')+'\')',
            'backgroundPosition':'0 0'
          }
        }
      }
    };
    
    
    
    var getNode = function(s){
      var c = s.split('.');
      var p = CFG;
      for(var i=0;i<c.length;i++){
        if(p[c[i]]) p = p[c[i]];
        else return {};
      }
      return p;
    };
    
    return {
      'desc':'默认皮肤',
      'get':function(s){return getNode(s);}
    };
  }
});