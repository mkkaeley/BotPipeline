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
        

        
        

       // //--------------------Running Powershell File 
        // const { spawn } = require('child_process');

        // // Define the PowerShell script path and arguments
        // const scriptPath = 'DifferentBot.ps1'; 
        // const scriptArgs = ['-ExecutionPolicy', 'Bypass', '-File', scriptPath];
        
        // // Spawn a new PowerShell process
        // const powershellProcess = spawn('powershell.exe', scriptArgs);
        
        // // Capture the output of the PowerShell script
        // powershellProcess.stdout.on('data', (data) => {
        //   console.log(`PowerShell script output: ${data}`);
        // });
        
        // // Handle any errors or completion of the PowerShell script
        // powershellProcess.on('error', (error) => {
        //   console.error(`Error executing PowerShell script: ${error}`);
        // });
        
        // powershellProcess.on('close', (code) => {
        //   if (code === 0) {
        //     console.log('PowerShell script executed successfully.');
        //   } else {
        //     console.error(`PowerShell script exited with code ${code}.`);
        //   }
        // });
        
        // // ----------- Direct trigger jenkins job
        //   var username = "darshana",
        //   password = "113e0e92afad783c190fc5d3d7eeef0cc0";
        //   const exurl = "http://"+username+":"+password+"@122.170.77.44:8003/job/Export_Checkin/build?token=test";
          
        //   request({ url: exurl }, (err, res) => {
        //     console.log(res.statusCode);   
        // }); 
    }
};

//  exec('git add . ', (error, stdout, stderr) => {
//           if (error) {
//             console.error('Error executing Git:', error);
//             return;
//           }

//           console.log('Git output:', stdout);
//         });