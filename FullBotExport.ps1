$export_header = @{
    'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTRhZWU4NWRiLTFkNTctNTRiNS05ZDBhLWU1YTM5OWNlOGViZiJ9.WR5FRO4WH0N0SaBU_SFV1JRoB-X_ZcQ6JbzG5MoL4Bc'
    'content-type' = 'application/json'
}
$export_body = '{
    "exportType": "published",
    "exportOptions": {
        "settings": [
            "botSettings",
            "botVariables",
            "ivrSettings"
        ],
        "tasks": [
            "botTask",
            "knowledgeGraph",
            "smallTalk"
        ],
        "nlpData": [
            "nlpSettings",
            "utterances",
            "patterns",
            "standardResponses"
        ]
    },
    
    "allTasks": true,
    "customDashboards": true,
    "IncludeDependentTasks": true
}'

$tmp=Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export" -Method Post -Headers $export_header -Body $export_body 
Start-Sleep -Seconds 1.5
$export_res = Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status" -Method Get -Headers $export_header 

Invoke-WebRequest $export_res.downloadURL -OutFile fullexport.zip 
Start-Sleep -Seconds 1.5

Expand-Archive -Path fullexport.zip -DestinationPath ./BotExport  -Force

# $upload_res_bot= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTFjMTlkN2E0LWEzNGYtNTMyZC1hMjJiLTkyNGM3YzgyNTA2ZiJ9.RSbwd13e9yPI45Pos9Xv5rrfFk8Qakd2QBU7F-3rxlQ'   -H 'content-type: multipart/form-data' -F file=@botDefinition.json -F fileContext=bulkImport -F fileExtension=json  
# $bot_definitionid= $upload_res_bot | ConvertFrom-Json 

# $upload_res_config= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTFjMTlkN2E0LWEzNGYtNTMyZC1hMjJiLTkyNGM3YzgyNTA2ZiJ9.RSbwd13e9yPI45Pos9Xv5rrfFk8Qakd2QBU7F-3rxlQ'   -H 'content-type: multipart/form-data' -F file=@config.json -F fileContext=bulkImport -F fileExtension=json  
# $bot_configid= $upload_res_config | ConvertFrom-Json 

# $bot_definitionid.fileID
# $bot_configid.fileID

# $import_header = @{
#     'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTFjMTlkN2E0LWEzNGYtNTMyZC1hMjJiLTkyNGM3YzgyNTA2ZiJ9.RSbwd13e9yPI45Pos9Xv5rrfFk8Qakd2QBU7F-3rxlQ'
#     'content-type' = 'application/json'
# }

# $import_body = '{
#     "botDefinition" : "'+$bot_definitionid.fileID+'",
#     "configInfo" : "'+$bot_configid.fileID+'",
#     "importOptions": {
#     "tasks": [
#         "botTask",
#         "knowledgeGraph",
#         "smallTalk"
#         ],
#     "nlpData": [
#         "nlpSettings",
#         "utterances",
#         "patterns",
#         "standardResponses"
#         ],
#     "settings": [
#         "botSettings",
#         "botVariables",
#         "ivrSettings"
#         ]
#  }

# }'

# Start-Sleep -Seconds 1.5
# $import_res=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-d06384bf-4180-5973-906b-6ef1507fd586/import" -Method Post -Body $import_body -Headers $import_header
# Start-Sleep -Seconds 1.5
# $import_res._id

# Start-Sleep -Seconds 5

# $status_url = "https://bots.kore.ai/api/public/bot/import/status/"+$import_res._id
# $status=Invoke-RestMethod -Uri $status_url -Headers $import_header

# $status.status




