float sdRoundRect( in vec2 p, in vec2 b, in float r ) {
    vec2 q = abs(p)-b+r;
    return min(max(q.x,q.y),0.0) + length(max(q,0.0)) - r;
}

vec4 normalBlend(vec4 src, vec4 dst) {
    float finalAlpha = src.a + dst.a * (1.0 - src.a);
    return vec4(
        (src.rgb * src.a + dst.rgb * dst.a * (1.0 - src.a)) / finalAlpha,
        finalAlpha
    );
}

float sigmoid(float t) {
    return 1.0 / (1.0 + exp(-t));
}

float cornerRadius = 32.0;
float blurRadius = 32.0f;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = fragCoord/iResolution.xy;

    vec2 center = iResolution.xy / 2.0;
    vec2 hsize = iResolution.xy / 4.0;
    
	float distShadow = clamp(sigmoid(sdRoundRect(fragCoord - center + vec2(0.0, iResolution.y / 20.0),
        hsize, cornerRadius + blurRadius) / blurRadius), 0.0, 1.0);
        
    float distRect = clamp(sdRoundRect(fragCoord - center, hsize, cornerRadius), 0.0, 1.0);

    vec3 col = vec3(distShadow);
    col = mix(vec3(1.0), col, distRect);
    
    vec4 finalColor = normalBlend(vec4(vec3(1.0), 0.6), vec4(col, 1.0));
    fragColor = finalColor;
}
