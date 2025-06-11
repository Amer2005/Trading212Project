export async function getAssetNames() {
  const res = await fetch('https://api.kraken.com/0/public/Assets');
  
  const { result } = await res.json();

  const names = Object.fromEntries(
    Object.entries(result).map(([code, data]) => [code, data.altname])
  );
  
  return names; 
}