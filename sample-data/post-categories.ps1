# Posts every category in categories.json to inventory-service
$categories = Get-Content -Raw -Encoding UTF8 "$PSScriptRoot\categories.json" | ConvertFrom-Json
foreach ($c in $categories) {
    $body = $c | ConvertTo-Json -Compress
    try {
        $r = Invoke-RestMethod -Uri "http://localhost:8088/api/inventory/categories" -Method Post `
             -ContentType "application/json; charset=utf-8" -Body ([Text.Encoding]::UTF8.GetBytes($body))
        Write-Host "OK   id=$($r.id)  $($r.code)  $($r.name)"
    } catch {
        Write-Host "FAIL $($c.name): $(if ($_.ErrorDetails.Message) { $_.ErrorDetails.Message } else { $_.Exception.Message })" -ForegroundColor Red
    }
}
