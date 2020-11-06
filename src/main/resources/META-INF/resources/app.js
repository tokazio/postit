const GET = 'get';
const POST = 'post';
const DELETE = 'delete';

const V1 = 'application/fr.tokazio.postit-1.0+json';

const API_POSTITS = '/api/postits/';
const API_USERS = '/api/users/';
const API_CATEGORIES = '/api/categories/';

Quasar.lang.set(Quasar.lang.fr)

new Vue({
    el: '#q-app',
    data: function () {
        return {
            mode: null,
            connection: null,
            connected: false,
            addDialog: false,
            deleteDialog: false,
            deletingId: -1,
            userDialog: true,
            editingId: -1,
            textEdit: '',
            user: '',
            postits: null,
            users: null,
            categories: null,
            selectedCat: null,
        }
    },
    methods: {

        confirmDelete: function(id){
            this.deleteDialog = true
            this.deletingId = id
        },

        deletePostit: function(){
            me = this;
            axios({
                method: DELETE,
                url: API_POSTITS+this.deletingId,
                headers: {
                    'Content-Type': V1,
                }
            }).then(function () {
                  me.notif('Supprimé','positive')
                  me.deletingId = -1
                  me.refresh()
                  me.connection.send('refresh');
            }, function(err){
                me.notif( err,'negative')
            })
        },

        liked: function(postit){
            if(postit!=undefined && postit.likedBy !=null && postit.likedBy.includes(this.user)){
                return 'fas fa-heart'
            }
            return 'far fa-heart'
        },

        like: function(id){
            console.log("like "+id+" returned: ");
            axios({
                method: POST,
                url: API_POSTITS+'like/'+id,
                headers: {
                    'Content-Type': V1,
                },
                data: {
                    user : this.user
                }
            }).then(function (response) {
                console.log(response.data);
                liking = response.data.liking
                user = response.data.user
                if(liking=='LIKE'){
                    text = user + " aime";
                    me.notif('Vous aimez','purple');
                }else{
                    text = user + " n'aime plus";
                    me.notif("Vous n'aimez plus",'purple');
                }
                me.notifAll(text,'positive')
                me.refresh();
            }, function(err){
                me.notif(err,'negative')
            });
        },

        showEdit: function(id){
            console.log('showEdit: '+id)
            this.selectedCat = null
            this.textEdit = ''
            this.editingId = null
            if(id!=null){
                //EDIT
                this.editingId = id
                me = this
                axios({
                    method: GET,
                    url: API_POSTITS+me.editingId,
                    headers: {
                        'Content-Type': V1,
                    }
                }).then(function (response) {
                    console.log(response.data)
                    me.textEdit = response.data.text
                    me.selectedCat = response.data.category
                })
            }
            this.addDialog = true
        },

        validEdit: function(){
             this.$refs.cat.validate()
              this.$refs.text.validate()

              if (this.$refs.cat.hasError || this.$refs.text.hasError) {
                this.formHasError = true
              }

            if(this.editingId==null){
                this.doAddPostit();
            }else{
                this.doEditPostit();
            }
        },

        doEditPostit: function(){
            console.log("doEditPostit");
            me = this;
            axios({
                method: POST,
                url: API_POSTITS+this.editingId,
                headers: {
                    'Content-Type': V1,
                },
                data: {
                    text: this.textEdit,
                    user: this.user,
                    category: this.selectedCat
                }
            }).then(function () {
                me.addDialog = false
                me.notif('Modifié','positive')
                me.textEdit = '';
                me.editingId = -1
                me.refresh();
                me.connection.send('refresh');
            }, function(err){
                me.notif(err,'negative')
            });
        },

        save: function(){
            me = this;
            axios({
                method: 'post',
                url: API_POSTITS+'save',
                headers: {
                    'Content-Type': V1,
                }
            }).then(function () {
                me.notif('Sauvegardé','positive')
                me.refresh();
            }, function(err){
                me.notif(err,'negative')
            });
        },

        doAddPostit: function(){
            newPostit = {
                 text: this.textEdit,
                 user: this.user,
                 category: this.selectedCat,
            }
            console.log("doAddPostit with:")
            console.log(newPostit)
            me = this;
            axios({
                method: POST,
                url: API_POSTITS,
                headers: {
                    'Content-Type': V1,
                },
                data: newPostit
            }).then(function () {
               me.addDialog = false
               me.notif('Ajouté','positive')
               me.textEdit = '';
               me.selectedCat = null
               me.refresh();
               me.connection.send('refresh');
            },function(err){
                 me.notif(err,'negative')
            });
        },

        refresh: function(){
            axios({
                method : GET,
                url : API_POSTITS,
                headers: {
                    'Content-Type': V1,
                }
            }).then(response => (this.postits = response.data));

        },

        refreshUsers: function(){
            axios({
                method : GET,
                url: API_USERS,
                headers: {
                    'Content-Type': V1,
                }
            }).then(response => (this.users = response.data));
        },

        connect: function() {
            if(this.user==null || this.user==''){
                return
            }
            this.userDialog = false
            console.log("Starting connection to WebSocket Server")
            this.connection = new WebSocket("ws://"+location.host+"/websock/"+this.user)

            me = this
            this.connection.onmessage = function(event) {
              console.log(event);
              if(event.data.includes('joined')){
                 me.notif(event.data,'info')
                 me.refreshUsers();
              }
              if(event.data.includes('left')){
                 me.notif(event.data,'info')
                 me.refreshUsers();
              }
              from = event.data.substring(9).split(': ')[0];
              if(from==me.user){
                return
              }
              message = event.data.substring(9).split(': ')[1];
              console.log(message);
              if(message.includes('notif')){
                  me.notif(message.substring(7),'purple')
                  me.refresh();
              }
              if(message.trim()=='refresh'){
                me.refresh();
              }
            }

            this.connection.onopen = function(event) {
              console.log(event)
              console.log("Successfully connected to the websocket server...")
              me.connected = true
              me.refreshUsers();
            }

            this.connection.onclose = function(event) {
              console.log(event)
              console.log("Websocket closed")
              me.connected = false
              me.notif('Déconnecté','info')
            }

            this.connection.onerror = function(event) {
              console.log(event)
              console.log("Websocket error")
              me.connected = false
              me.notif('Déconnecté','info')
            }


          },

          syncColor: function(){
                return this.connected ? 'green' : 'red'
          },

          postitColor : function(postit){

                t = 'text-grey-1 '

                if(postit.category.value=='ARRETER'){
                    return t+'bg-red';
                }
                if(postit.category.value=='CONTINUER'){
                    return t+'bg-pink';
                }
                if(postit.category.value=='MOINS'){
                    return t+'bg-purple';
                }
                if(postit.category.value=='PLUS'){
                    return t+'bg-deep-purple';
                }
                if(postit.category.value=='COMMENCER'){
                    return t+'bg-indigo';
                }

                return 'bg-amber'

            },

            notif: function(text,aColor){
                  this.$q.notify({
                    position: 'bottom-left',
                    message: text,
                    color: aColor
                  })
            },

            notifAll: function(text){
                me.connection.send('notif::'+text);
            },



    },
    mounted () {
        this.refresh();
        me = this
        axios({
            method: GET,
            url: API_CATEGORIES,
            headers: {
                'Content-Type': V1,
            }
        }).then(function(response){
            me.categories = response.data
            console.log(me.categories)
        });


    }
})
