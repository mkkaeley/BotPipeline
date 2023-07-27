const request = require('request'); 


module.exports = {
    botId: 'st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132', 
    botName: 'Demobot1',
    
    on_variable_update: function(requestId, data, callback) {
        var event = data.eventType;
        console.log("event----------->", event);       
        

        // //---------------------------Running Java File (For that we rquired Child process and cmd is "npm install child_process")
        const { exec } = require('child_process');
           exec('java -cp ".;./lib/json-20230618.jar" Export.java', (error, stdout, stderr) => {
          if (error) {
            console.error('Error executing Java file:', error);
            return;
          }

          console.log('Java file output:', stdout);
        });      
    }
};

