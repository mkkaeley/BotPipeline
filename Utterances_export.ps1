$export_header = @{
    'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWQ1NDU2OGEwLTQzMTctNTY1Ni05ODI0LTBiOTEzNDViNmM3MiJ9.mJDo_fnMn4vNaJORv5Z73Nl8J4Lnu7tPYDGVxNb1wnQ'
    'content-type' = 'application/json'
}

$tmp=Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/mlexport?state=configured&type=json" -Method Post -Headers $export_header
Start-Sleep -Seconds 1.5
$export_res = Invoke-restMethod -Uri "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/mlexport/status" -Method Get -Headers $export_header 

Invoke-WebRequest $export_res.downloadURL -OutFile utterance.json

git config --global user.email "darshanavadalia04@gmail.com"
git config --global user.name "darshana0406"

git add ExportBot/Demobot1_MLUtterances.json
git commit -m "Bot Files Update"

git push origin main

# cd ExportBot

# $upload_res= curl.exe --silent -X POST https://bots.kore.ai/api/public/uploadfile -H 'auth: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiYXBwSWQiOiJjcy1lZmE5NWI3OC03ODQ2LTU0OTAtYjc3NS04NTgyZGE4MWNkYTUifQ.hNQg3nPg8tHtuMyW_9948C27IxRiPHclYggIfSQZbm0'   -H 'content-type: multipart/form-data' -F file=@utterance.json -F fileContext=bulkImport -F fileExtension=json  

# $id= $upload_res | ConvertFrom-Json 

# $import_header = @{
#     'auth' = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTk2YTgyNjYzLTFjMTQtNWM5NC05Y2IxLWQ2ZWMwMTEyN2M5NSJ9.wyB5aRG5O-7KtpdJwYlkCuloyFipDA8U500xCv8z0Kk'
#     'content-type' = 'application/json'
# }

# $import_body = '{
#     "fileName":"utterance.json",
#     "fileId": "'+$id.fileID+'"
# }'
# Start-Sleep -Seconds 1.5
# $import_res=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/mlimport" -Method Post -Body $import_body -Headers $import_header
# Start-Sleep -Seconds 1.5

# $status_url = "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/mlimport/status/"+$import_res._id
# $status=Invoke-RestMethod -Uri $status_url -Headers $import_header

# $status.status

# $train=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/ml/train" -Headers $import_header -Method Post
# $train.message
# Start-Sleep -Seconds 10
# $trainstatus=Invoke-RestMethod -Uri "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/ml/train/status" -Headers $import_header -Method Get
# $trainstatus.trainingStatus
