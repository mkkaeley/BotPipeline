const request = require('request'); 


module.exports = {
    botId: 'st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132', 
    botName: 'Demobot1',
    
    on_variable_update: function(requestId, data, callback) {
        var event = data.eventType;
        console.log("event----------->", event);       
        
        const { spawn } = require('child_process');

        // Define the PowerShell script path and arguments
        const scriptPath = 'FullBotExport.ps1';
        const scriptArgs = ['-ExecutionPolicy', 'Bypass', '-File', scriptPath];
        
        // Spawn a new PowerShell process
        const powershellProcess = spawn('powershell.exe', scriptArgs);
        
        // Capture the output of the PowerShell script
        powershellProcess.stdout.on('data', (data) => {
          console.log(`PowerShell script output: ${data}`);
        });
        
        // Handle any errors or completion of the PowerShell script
        powershellProcess.on('error', (error) => {
          console.error(`Error executing PowerShell script: ${error}`);
        });
        
        powershellProcess.on('close', (code) => {
          if (code === 0) {
            console.log('PowerShell script executed successfully.');
          } else {
            console.error(`PowerShell script exited with code ${code}.`);
          }
        });
        
      //   var username = "darshana",
      //   password = "1154a47b7eb92d7c5b09ac25c3f0abdb19";
      //   const exurl = "http://"+username+":"+password+"@122.170.77.44:8003/job/Botkit%20Job/build?token=123";
        
      //   request({ url: exurl }, (err, res) => {
      //     console.log(res.statusCode);   
      // }); 
    }
};

