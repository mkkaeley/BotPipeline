
$upload_res_bot= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWVmYTk1Yjc4LTc4NDYtNTQ5MC1iNzc1LTg1ODJkYTgxY2RhNSJ9.9gODvgXbG2DOtOvVfLyM2Jea-G2Tvx69C0JnbANpX7A'   -H 'content-type: multipart/form-data' -F file=@botDefinition.json -F fileContext=bulkImport -F fileExtension=json  
$bot_definitionid= $upload_res_bot | ConvertFrom-Json 

$upload_res_config= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWVmYTk1Yjc4LTc4NDYtNTQ5MC1iNzc1LTg1ODJkYTgxY2RhNSJ9.9gODvgXbG2DOtOvVfLyM2Jea-G2Tvx69C0JnbANpX7A'   -H 'content-type: multipart/form-data' -F file=@config.json -F fileContext=bulkImport -F fileExtension=json  
$bot_configid= $upload_res_config | ConvertFrom-Json 

$bot_definitionid.fileID
$bot_configid.fileID

$import_header = @{
    'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWVmYTk1Yjc4LTc4NDYtNTQ5MC1iNzc1LTg1ODJkYTgxY2RhNSJ9.9gODvgXbG2DOtOvVfLyM2Jea-G2Tvx69C0JnbANpX7A'
    'content-type' = 'application/json'
}

$import_body = '{
    "botDefinition" : "'+$bot_definitionid.fileID+'",
    "configInfo" : "'+$bot_configid.fileID+'",
    "importOptions": {
	"nlpData": [
		"training_data",
		"bot_synonyms",
		"nlpSettings",
		"defaultDialog",
		"standardResponses",
        "utterances",
        "patterns"
	],
	"settings": [
		"botSettings",
		"ivrSettings",
		"botVariables"
	],
      "tasks": [
            "botTask",
            "knowledgeGraph",
            "smallTalk"
        ],
	"options": {
		"utterances": {
		"append": true,
		"replace": true
		}
	},
	"botComponents": [
		"linkedBots",
		"smallTalk"
	],
	"customDashboard": true
} 

}'

Start-Sleep -Seconds 1.5
$import_res=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/import" -Method Post -Body $import_body -Headers $import_header
Start-Sleep -Seconds 1.5
$import_res._id

Start-Sleep -Seconds 5

$status_url = "https://bots.kore.ai/api/public/bot/import/status/"+$import_res._id
$status=Invoke-RestMethod -Uri $status_url -Headers $import_header

$status.status