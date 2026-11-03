# Posts every product in products.json to inventory-service
$products = Get-Content -Raw -Encoding UTF8 "$PSScriptRoot\products.json" | ConvertFrom-Json
foreach ($p in $products) {
    $body = $p | ConvertTo-Json -Compress
    try {
        $r = Invoke-RestMethod -Uri "http://localhost:8088/api/inventory/products" -Method Post `
             -ContentType "application/json; charset=utf-8" -Body ([Text.Encoding]::UTF8.GetBytes($body))
        Write-Host "OK   $($r.sku)  $($r.name)"
    } catch {
        Write-Host "FAIL $($p.name): $(if ($_.ErrorDetails.Message) { $_.ErrorDetails.Message } else { $_.Exception.Message })" -ForegroundColor Red
    }
}
